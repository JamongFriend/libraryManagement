package project.libraryManagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.libraryManagement.domain.*;
import project.libraryManagement.repository.BookRepository;
import project.libraryManagement.repository.MemberRepository;
import project.libraryManagement.repository.RentalRepository;
import project.libraryManagement.repository.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;

    // ======================= 사용자영역 =======================
    @Transactional
    public Long rentalBook(Long memberId, Long bookId, int count, int period) {
        Member member = memberRepository.findOne(memberId);
        Book book = bookRepository.findOne(bookId);

        Reservation reservation = reservationRepository.findMemberAndBook(memberId, bookId);

        RentalBook rentalBook = RentalBook.createRentalBook(book, count, period);
        Rental rental = Rental.createRental(member, reservation, rentalBook);

        rentalRepository.save(rental);
        return rental.getId();
    }

    @Transactional
    public void returnBook(Long rentalId, Long memberId) {
        Rental rental = rentalRepository.findOne(rentalId);
        if (rental == null) {
            throw new IllegalArgumentException("대여 내역 없음");
        }
        if (!rental.getMember().getId().equals(memberId))
            throw new IllegalStateException("본인 대여만 반납할 수 있습니다.");
        rental.returnRental(); // 반납 처리

        allocateToNextReservationIfAny(rental.getRentalBooks().get(0).getBook().getId());
    }

    @Transactional
    public List<Rental> findByMemberId(Long memberId) {
        return rentalRepository.findByMember(memberId);
    }

    // ======================= 관리자 영역 =======================

    @Transactional
    public List<Rental> findAll(String status) {
        if (status == null || status.isBlank()) {
            return rentalRepository.findAllOrderByRentalDateDesc();
        }
        return rentalRepository.findByStatusOrderByRentalDateDesc(RentalStatus.valueOf(status));
    }

    @Transactional
    public void forceReturn(Long rentalId) {
        Rental rental = rentalRepository.findOne(rentalId);
        if (rental == null) throw new IllegalArgumentException("대여가 존재하지 않습니다.");
        rental.returnRental();
        // 반납된 책에 대하여 예약된 유저에게 자동 대여
        if (!rental.getRentalBooks().isEmpty()) {
            Long bookId = rental.getRentalBooks().get(0).getBook().getId();
            allocateToNextReservationIfAny(bookId);
        }
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void reduceRentalPeriod() {
        List<Rental> rentals = rentalRepository.findActiveWithPositiveDaysLeft();
        rentals.forEach(Rental::decreaseRentalPeriod);
    }

    @Transactional
    public void allocateToNextReservationIfAny(Long bookId) {
        Book book = bookRepository.findOne(bookId);
        if(book == null ||  book.getStockQuantity() <= 0) return;

        Reservation earlistReservaion = reservationRepository.findEarlistByBook(bookId);
        if(earlistReservaion == null) return;
        Member member = earlistReservaion.getMember();

        int count = 1;
        int period = 14;
        RentalBook rentalBook = RentalBook.createRentalBook(book, count, period);
        Rental rental = Rental.createRental(member, earlistReservaion, rentalBook);
        rentalRepository.save(rental);

        earlistReservaion.setStatus(ReservationStatus.FULFILLED);
    }
}
