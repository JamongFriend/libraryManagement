package project.libraryManagement.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RentalBook {
    @Id @GeneratedValue
    @Column(name = "rental_book_id")
    private Long id;

    private int count;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    protected RentalBook() {}
}
