package project.libraryManagement.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Reservation {
    @Id @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer bookQuantity;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "reservation")
    private final List<Rental> rentalList = new ArrayList<>();

    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public static Reservation createReservation(Member member, Book book, int count) {
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setBook(book);
        reservation.setBookQuantity(count);
        reservation.setStatus(ReservationStatus.RESERVATION);
        reservation.setReservationDate(LocalDateTime.now());
        return reservation;
    }

    public void cancel() {
        this.setStatus(ReservationStatus.CANCEL);
    }
}
