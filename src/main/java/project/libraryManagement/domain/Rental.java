package project.libraryManagement.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Rental {
    @Id
    @GeneratedValue
    @Column(name = "rental_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private List<RentalBook> rentalBooks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    private LocalDate rentalDate;
    private LocalDate returnDate;

    public Rental(Member member, List<RentalBook> rentalBooks, RentalStatus status, LocalDate rentalDate, LocalDate returnDate) {
        this.member = member;
        this.rentalBooks = rentalBooks;
        this.status = status;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }
}
