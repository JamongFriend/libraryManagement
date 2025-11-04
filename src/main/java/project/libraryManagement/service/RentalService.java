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
    public void returnBook(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null) {
            throw new IllegalArgumentException("대여 내역 없음");
        }
        rental.returnRental(); // 반납 처리

        // 반납된 책에 대하여 예약된 유저에게 자동 대여
        rental.getRentalBooks().forEach(rb -> {
            Long bookId = rb.getBook().getId();
            allocateToNextReservationIfAny(bookId);
        });

    }

    @Transactional
    public void allocateToNextReservationIfAny(Long bookId) {
        Book book = bookRepository.findOne(bookId);
        if(book == null ||  book.getStockQuantity() <= 0) return;

        Reservation EarlistReservaion = reservationRepository.findEarlistByBook(bookId);
        if(EarlistReservaion == null) return;
        Member member = EarlistReservaion.getMember();

        int count = 1;
        int period = 14;
        RentalBook rentalBook = RentalBook.createRentalBook(book, count, period);
        Rental rental = Rental.createRental(member, EarlistReservaion, rentalBook);
        rentalRepository.save(rental);

        EarlistReservaion.setStatus(ReservationStatus.FULFILLED);
    }

    @Transactional
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void reduceRentalPeriod() {
        List<Rental> rentals = rentalRepository.findActiveWithPositiveDaysLeft();
        rentals.forEach(Rental::decreaseRentalPeriod);
    }
}
