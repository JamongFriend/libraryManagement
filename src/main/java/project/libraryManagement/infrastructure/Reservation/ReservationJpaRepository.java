package project.libraryManagement.infrastructure.Reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.Reservation.Reservation;

@Repository
public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
}
