package project.libraryManagement.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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

    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;

    public void addRentalBook(RentalBook rentalBook) {
        rentalBooks.add(rentalBook);
        rentalBook.setRental(this);
    }

    public void returnRental(){
        if(this.status == RentalStatus.RENTAL){
            this.status = RentalStatus.RETURNED;
            this.returnDate = LocalDateTime.now();
            for (RentalBook rentalBook : new ArrayList<>(rentalBooks)) {
                returnRentalBook(rentalBook);
            }
        } else {
            throw new IllegalStateException("이미 반납된 도서입니다.");
        }

    }

    public void returnRentalBook(RentalBook rentalBook){
        if(!rentalBooks.contains(rentalBook)){
            throw new IllegalArgumentException("해당 대여 내역이 존재하지 않습니다");
        }

        rentalBook.returnBook();
        rentalBook.setRental(null);
    }

    public void decreaseRentalPeriod() {
        for (RentalBook rentalBook : rentalBooks) {
            rentalBook.decreasePeriod();
        }
    }

    public static Rental createRental(Member member, Reservation reservation, RentalBook... rentalBooks) {
        Rental rental = new Rental();
        rental.setMember(member);

        if(reservation != null)
            rental.setReservation(reservation);

        for(RentalBook rentalBook : rentalBooks){
            rental.addRentalBook(rentalBook);
        }
        rental.setStatus(RentalStatus.RENTAL);
        rental.setRentalDate(LocalDateTime.now());
        return rental;
    }
}
