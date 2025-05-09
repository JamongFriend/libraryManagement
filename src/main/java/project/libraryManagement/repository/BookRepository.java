package project.libraryManagement.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.Book;
import project.libraryManagement.domain.Member;

import java.util.List;

@Repository
public class BookRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Book book) {
        if(book.getId() == null) {
            em.persist(book);
        }
        else {
            em.merge(book);
        }
    }

    public void deleteBook(Long bookId){
        if(!canDeleteBook(bookId)) {
            throw new IllegalArgumentException("대여중인 책은 삭제 불가능");
        }
        Book book = em.find(Book.class, bookId);
        if(book != null){
            em.remove(book);
        }
    }

    public boolean canDeleteBook(Long bookId) {
        Long count = em.createQuery("SELECT COUNT(r) FROM RentalBook r WHERE r.book.id = :bookId", Long.class)
                .setParameter("bookId", bookId)
                .getSingleResult();

        count += em.createQuery("SELECT COUNT(r) FROM ReservationBook r WHERE r.book.id = :bookId", Long.class)
                .setParameter("bookId", bookId)
                .getSingleResult();

        return count == 0;
    }

    public Book findOne(Long id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll() {
        return em.createQuery("select m from Book m", Book.class).getResultList();
    }
}
