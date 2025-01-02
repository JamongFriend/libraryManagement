package project.libraryManagement.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RentalBook {
    @Id @GeneratedValue
    @Column(name = "rental_book_id")
    private Long id;

    private int count;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
