package project.libraryManagement.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.libraryManagement.domain.book.Book;
import project.libraryManagement.domain.member.Member;
import project.libraryManagement.domain.reservation.Reservation;
import project.libraryManagement.domain.reservation.ReservationBook;
import project.libraryManagement.infrastructure.book.BookJpaRepository;
import project.libraryManagement.infrastructure.member.MemberJpaRepository;
import project.libraryManagement.domain.reservation.ReservationRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
    private final MemberJpaRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final BookJpaRepository bookRepository;
    private final RentalService rentalService;

    @Transactional
    public void reservationBook(Long memberId, Long bookId, int count){
        Member member = memberRepository.findOne(memberId);
        Book book =bookRepository.findOne(bookId);

        ReservationBook reservationBook = ReservationBook.createReservationBook(book, count);
        Reservation reservation = Reservation.createReservation(member, reservationBook);
        reservationRepository.save(reservation);

        // 재고가 남을 시 자동 대여 시도
        if(book.getStockQuantity() > 0) {
            rentalService.allocateToNextReservationIfAny(book.getId());
        }
    }

    @Transactional
    public void cancel(Long id) {
        Reservation reservation = reservationRepository.findReservationBook(id);
        reservation.cancel();
    }

    @Transactional
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Reservation> findByMemberId(Long memberId) {
        return reservationRepository.findByMemberId(memberId);
    }

    @Transactional
    public void cancelByOwner(Long memberId, Long reservationId){
        Reservation r = reservationRepository.findByIdAndMemberId(reservationId, memberId);
        if (r == null) {
            throw new IllegalArgumentException("본인 예약이 아니거나 존재하지 않습니다.");
        }
        cancel(reservationId); // ← 기존 로직 재사용
    }
}
