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
    //책 삭제
    public void deleteBook(Long bookId){
        if(!canDeleteBook(bookId)) {
            throw new IllegalArgumentException("대여중인 책은 삭제 불가능");
        }
        Book book = em.find(Book.class, bookId);
        if(book != null){
            em.remove(book);
        }
    }
    //책 삭제가능 여부
    public boolean canDeleteBook(Long bookId) {
        Long count = em.createQuery("SELECT COUNT(r) FROM RentalBook r WHERE r.book.id = :bookId", Long.class)
                .setParameter("bookId", bookId)
                .getSingleResult();

        count += em.createQuery("SELECT COUNT(r) FROM ReservationBook r WHERE r.book.id = :bookId", Long.class)
                .setParameter("bookId", bookId)
                .getSingleResult();

        return count == 0;
    }
    //책을 id를 통해 조회
    public Book findOne(Long id) {
        return em.find(Book.class, id);
    }
    //책 전체 조회
    public List<Book> findAll() {
        return em.createQuery("select m from Book m", Book.class).getResultList();
    }

    //재고가 0이 되는것을 방지
    public Book findByIdForUpdate(Long id) {
        return em.find(Book.class, id, jakarta.persistence.LockModeType.PESSIMISTIC_WRITE);
    }
}
