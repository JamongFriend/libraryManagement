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
        Rental rental = rentalRepository.findRentaledBook(rentalId);
        rental.returnRental();
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void reduceRentalPeriod() {
        List<Rental> rentals = rentalRepository.findAll();
        for(Rental rental : rentals){
            rental.decreaseRentalPeriod();
        }
    }
}
