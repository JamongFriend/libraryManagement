package project.libraryManagement.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private final List<RentalBook> rentalBookList = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private final List<Reservation> reservationList = new ArrayList<>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        this.stockQuantity -= quantity;
    }


}
