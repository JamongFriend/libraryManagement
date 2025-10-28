package project.libraryManagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.libraryManagement.domain.Book;
import project.libraryManagement.domain.Category;
import project.libraryManagement.domain.dto.ApiBookDto;
import project.libraryManagement.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final LibraryApiService libraryApiService;

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(Long id, String title, String isbn,
                           String publisher, String publicationYear,
                           String author, Category category, int stockQuantity){
        Book book = bookRepository.findOne(id);
        book.updateInfo(title, author, isbn, publisher, publicationYear, category, stockQuantity);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteBook(id);
    }

    @Transactional
    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book findOne(Long id) {
        return bookRepository.findOne(id);
    }

    @Transactional
    public Long upsertByIsbn(String isbn, int addStock){
        Book existing = bookRepository.findByIsbn(isbn);
        if(existing != null) {
            existing.addStock(addStock);
            return existing.getId();
        }

        ApiBookDto dto = libraryApiService.fetchByIsbn(isbn);
        if(dto == null || dto.getTitle() == null)
            throw new IllegalArgumentException("해당 ISBN의 도서 정보를 찾을 수 없습니다");

        Book book = Book.create(
                dto.getTitle(),
                dto.getIsbn(),
                dto.getAuthor(),
                dto.getPublisher(),
                dto.getPublicationYear(),
                dto.getThumbnailUrl(),
                null,                // or Category.SOMETHING
                addStock
        );

        bookRepository.save(book);
        return book.getId();
    }
}
