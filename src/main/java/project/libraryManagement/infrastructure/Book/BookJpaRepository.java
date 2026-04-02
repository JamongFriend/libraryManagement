package project.libraryManagement.infrastructure.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.Book.Book;
import project.libraryManagement.domain.Book.BookRepository;

import java.util.List;

@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long> {

}
