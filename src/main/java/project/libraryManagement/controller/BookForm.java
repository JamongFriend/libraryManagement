package project.libraryManagement.controller;

import lombok.Getter;
import lombok.Setter;
import project.libraryManagement.domain.Category;

@Getter @Setter
public class BookForm {
    private Long id;
    private String name;
    private String author;
    private String isbn;
    private Category category;
    private int stockQuantity;
}
