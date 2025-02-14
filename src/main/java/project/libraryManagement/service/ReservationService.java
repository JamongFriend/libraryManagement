package project.libraryManagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.libraryManagement.domain.Book;
import project.libraryManagement.domain.Member;
import project.libraryManagement.domain.Reservation;
import project.libraryManagement.domain.ReservationBook;
import project.libraryManagement.repository.BookRepository;
import project.libraryManagement.repository.MemberRepository;
import project.libraryManagement.repository.ReservationRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
    private MemberRepository memberRepository;
    private ReservationRepository reservationRepository;
    private BookRepository bookRepository;

    @Transactional
    public Long reservationBook(Long memberId, Long bookId, int count){
        Member member = memberRepository.findOne(memberId);
        Book book =bookRepository.findOne(bookId);

        ReservationBook reservationBook = ReservationBook.createReservationBook(book, count);
        Reservation reservation = Reservation.createReservation(member, reservationBook);
        reservationRepository.save(reservation);
        return reservation.getId();
    }

    public void cancel(Long id) {
        Reservation reservation = reservationRepository.findReservationBook(id);
        reservation.cancel();
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
}
