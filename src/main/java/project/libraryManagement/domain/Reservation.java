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

    @ManyToOne
    private Book book;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationBook> reservationBooks = new ArrayList<>();

    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public void addReservationBook(ReservationBook reservationBook) {
        reservationBooks.add(reservationBook);
        reservationBook.setReservation(this);
    }

    public void cancel() {
        if(this.status == ReservationStatus.RESERVATION){
            this.status = ReservationStatus.CANCEL;
            for(ReservationBook reservationBook : new ArrayList<>(reservationBooks)){
                reservationBook.cancel();
            }
        }
    }

    public static Reservation createReservation(Member member, ReservationBook... reservationBooks) {
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        for(ReservationBook reservationBook : reservationBooks) {
            reservation.addReservationBook(reservationBook);
        }
        reservation.setStatus(ReservationStatus.RESERVATION);
        reservation.setReservationDate(LocalDateTime.now());
        return reservation;
    }

    protected Reservation() {}

}
