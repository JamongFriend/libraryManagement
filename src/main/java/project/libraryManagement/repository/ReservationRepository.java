package project.libraryManagement.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.Reservation;
import project.libraryManagement.domain.ReservationStatus;

import java.util.List;

@Repository
public class ReservationRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Reservation reservation){
        em.persist(reservation);
    }

    //유저, 예약한 책 조회
    public Reservation findMemberAndBook(Long memberId, Long bookId){
        try {
            return em.createQuery("SELECT r FROM Reservation r WHERE r.member.id = :memberId AND r.book.id = :bookId",
                            Reservation.class)
                    .setParameter("memberId", memberId)
                    .setParameter("bookId", bookId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // 예약된 책 조회
    public Reservation findReservationBook(Long id) {
        return em.find(Reservation.class, id);
    }

    // 모두 조회
    public List<Reservation> findAll(){
        return em.createQuery("select m from Reservation m", Reservation.class)
                .getResultList();
    }

    // 제일 먼저 예약한 책 조회
    public Reservation findEarlistByBook(Long bookId) {
        List<Reservation> list = em.createQuery("SELECT r FROM Reservation r " +
                "JOIN r.reservationBooks rb " +
                "WHERE rb.book.id = :bookId AND r.status = :status " +
                "ORDER BY r.reservationDate ASC", Reservation.class)
                .setParameter("bookId", bookId)
                .setParameter("status", ReservationStatus.RESERVATION)
                .setMaxResults(1)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
}
