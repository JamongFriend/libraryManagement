package project.libraryManagement.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.Reservation;

import java.util.List;

@Repository
public class ReservationRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Reservation reservation){
        em.persist(reservation);
    }

    public Reservation findMemerAndBook(Long memberId, Long bookId){
        try{
            return em.createQuery("SELECT r FROM Reservation r WHERE r.memberId = :memberId AND r.bookId = :bookId",
                            Reservation.class)
                    .setParameter("memberId", memberId)
                    .setParameter("bookId", bookId)
                    .getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
    }

    public Reservation findReservationBook(Long id) {
        return em.find(Reservation.class, id);
    }

    public List<Reservation> findAll(){
        return em.createQuery("select m from Reservation m", Reservation.class)
                .getResultList();
    }
}
