package project.libraryManagement.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.Reservation;

@Repository
public class ReservationRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Reservation reservation){
        em.persist(reservation);
    }

    public Reservation findOne(Long id){
        return em.find(Reservation.class, id);
    }
    
}
