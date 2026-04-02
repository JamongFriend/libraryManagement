package project.libraryManagement.infrastructure.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.reservation.Reservation;

@Repository
public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
}
