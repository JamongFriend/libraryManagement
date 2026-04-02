package project.libraryManagement.infrastructure.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.book.Book;

@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long> {

}
