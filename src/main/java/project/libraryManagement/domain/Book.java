package project.libraryManagement.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String bookName;

    private int stockQuantity;

    private String author;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "rental_book_id")
    private final List<RentalBook> rentalBookList = new ArrayList<>();

    @OneToMany(mappedBy = "reservation_id")
    private final List<Reservation> reservationList = new ArrayList<>();
}
