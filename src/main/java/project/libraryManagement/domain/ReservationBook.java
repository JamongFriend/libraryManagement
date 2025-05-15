package project.libraryManagement.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "reservation_book")
public class ReservationBook {
    @Id @GeneratedValue
    @Column(name = "reservation_book_id")
    private Long id;

    private int bookQuantity;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public void cancel() {
        this.book.addStock(bookQuantity);
        this.reservation = null;
    }

    public static ReservationBook createReservationBook(Book book, int count) {
        ReservationBook reservationBook = new ReservationBook();
        reservationBook.setBook(book);
        reservationBook.setBookQuantity(count);
        book.removeStock(count);
        return reservationBook;
    }

    protected ReservationBook() {}
}
