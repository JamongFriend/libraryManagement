package project.libraryManagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.libraryManagement.domain.Book;
import project.libraryManagement.domain.Category;
import project.libraryManagement.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Long id, String name, String author, Category category, int stockQuantity){
        Book book = bookRepository.findOne(id);
        book.setBookName(name);
        book.setAuthor(author);
        book.setCategory(category);
        book.setStockQuantity(stockQuantity);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteBook(id);
    }

    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

    public Book findOne(Long id) {
        return bookRepository.findOne(id);
    }
}
