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
    private String title;
    private int stockQuantity;
    private String author;
    private String isbn;
    private String publisher;
    private String publicationYear;
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private final List<RentalBook> rentalBookList = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private final List<ReservationBook> reservationList = new ArrayList<>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        this.stockQuantity -= quantity;
    }

    public Book(String title, int stockQuantity, String author, String isbn, String publisher, String publicationYear, String thumbnailUrl, Category category) {
        this.title = title;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.thumbnailUrl = thumbnailUrl;
        this.category = category;
    }

    public static Book create(String title, String isbn, String author,
                              String publisher, String publicationYear,
                              String thumbnailUrl, Category category,
                              int stockQuantity) {
        Book b = new Book();
        b.setTitle(title);
        b.setIsbn(isbn);
        b.setAuthor(author);
        b.setPublisher(publisher);
        b.setPublicationYear(publicationYear);
        b.setThumbnailUrl(thumbnailUrl);
        b.setCategory(category);
        b.setStockQuantity(stockQuantity);
        return b;
    }

    protected Book() {}

}
