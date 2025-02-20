package project.libraryManagement.controller;

import lombok.Getter;
import lombok.Setter;
import project.libraryManagement.domain.Category;

@Getter @Setter
public class BookForm {
    private String id;
    private String name;
    private String author;
    private Category category;
    private int stockQuantity;
}
