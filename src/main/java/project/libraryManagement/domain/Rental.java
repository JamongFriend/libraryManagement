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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL)
    private List<RentalBook> rentalBooks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = true)
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    private LocalDate rentalDate;
    private LocalDate returnDate;

    public void addRentalBook(RentalBook rentalBook) {
        rentalBooks.add(rentalBook);
        rentalBook.setRental(this);
    }

    protected Rental() {};

    public Rental(Member member, List<RentalBook> rentalBooks, Reservation reservation, RentalStatus status, LocalDate rentalDate, LocalDate returnDate) {
        this.member = member;
        this.reservation = reservation;
        this.status = status;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        for (RentalBook rentalBook : rentalBooks) {
            addRentalBook(rentalBook);
        }
    }
}
