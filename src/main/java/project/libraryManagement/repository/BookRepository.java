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

    public Book findOne(Long id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll() {
        return em.createQuery("select m from Book m", Book.class).getResultList();
    }
}
