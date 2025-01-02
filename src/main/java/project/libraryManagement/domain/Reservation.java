package project.libraryManagement.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    private LocalDate reservationDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
