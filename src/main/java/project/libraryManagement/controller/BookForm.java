package project.libraryManagement.controller;

import lombok.Getter;
import lombok.Setter;
import project.libraryManagement.domain.Book;
import project.libraryManagement.domain.Category;

@Getter @Setter
public class BookForm {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String publicationYear;
    private Category category;
    private String thumbnailUrl;
    private int stockQuantity;

    public static BookForm of(Book book) {
        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setTitle(book.getTitle());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());
        form.setPublisher(book.getPublisher());
        form.setPublicationYear(book.getPublicationYear());
        form.setCategory(book.getCategory());
        form.setThumbnailUrl(book.getThumbnailUrl());
        form.setStockQuantity(book.getStockQuantity());
        return form;
    }
}
