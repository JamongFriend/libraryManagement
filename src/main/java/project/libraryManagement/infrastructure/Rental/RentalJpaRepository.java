package project.libraryManagement.infrastructure.Rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.Rental.Rental;

@Repository
public interface RentalJpaRepository extends JpaRepository<Rental, Long> {
}
