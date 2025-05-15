package project.libraryManagement.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rental_book")
@Getter @Setter
public class RentalBook {
    @Id @GeneratedValue
    @Column(name = "rental_book_id")
    private Long id;

    private int count;
    private int period;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    public void returnBook() {
        this.book.addStock(count);
    }

    public void decreasePeriod() {
        if (this.period > 0) {
            this.period--;
        }
    }

    protected RentalBook() {}

    public static RentalBook createRentalBook(Book book, int count, int period) {
        RentalBook rentalBook = new RentalBook();
        rentalBook.setBook(book);
        rentalBook.setCount(count);
        rentalBook.setPeriod(period);
        book.removeStock(count);
        return rentalBook;
    }
}
