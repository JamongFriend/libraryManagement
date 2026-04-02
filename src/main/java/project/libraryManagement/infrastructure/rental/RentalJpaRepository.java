package project.libraryManagement.infrastructure.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.rental.Rental;

@Repository
public interface RentalJpaRepository extends JpaRepository<Rental, Long> {
}
