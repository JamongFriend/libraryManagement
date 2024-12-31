package project.libraryManagement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String name;

    private String writer;
}
