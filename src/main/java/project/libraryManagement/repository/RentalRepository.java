package project.libraryManagement.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.Rental;
import project.libraryManagement.domain.RentalStatus;

import java.util.List;

@Repository
public class RentalRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Rental rental){
        em.persist(rental);
    }

    public List<Rental> findAll() {
        return em.createQuery("select m from Rental m", Rental.class)
                .getResultList();
    }

    public Rental findOne(Long id) {
        return em.find(Rental.class, id);
    }

    public List<Rental> findByMember(Long memberId) {
        return em.createQuery("select r " +
                                "from Rental r " +
                                "where r.member.id = :memberId " +
                                "order by r.rentalDate desc", Rental.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    // 목록(N+1 방지용) - 멤버/도서 즉시 로딩
    public List<Rental> findActiveWithMemberAndBook() {
        return em.createQuery(
                        "select r from Rental r " +
                                "join fetch r.member " +
                                "join fetch r.book " +
                                "where r.status = :status", Rental.class)
                .setParameter("status", RentalStatus.RENTAL)
                .getResultList();
    }

    // 중복 대여 방지(회원+도서 기준 활성 존재 여부)
    public boolean existsActiveByMemberAndBook(Long memberId, Long bookId) {
        Long count = em.createQuery(
                        "select count(r) from Rental r " +
                                "where r.member.id = :mid and r.book.id = :bid and r.status = :status", Long.class)
                .setParameter("mid", memberId)
                .setParameter("bid", bookId)
                .setParameter("status", RentalStatus.RENTAL)
                .getSingleResult();
        return count > 0;
    }

    // 스케줄러 최적화(남은 기간 > 0 인 활성 대여만)
    public List<Rental> findActiveWithPositiveDaysLeft() {
        return em.createQuery(
                        "select r from Rental r " +
                                "where r.status = :status and r.daysLeft > 0", Rental.class)
                .setParameter("status", RentalStatus.RENTAL)
                .getResultList();
    }

    // 전체 목록 최신순(관리자용)
    public List<Rental> findByMemberIdOrderByRentalDateDesc(Long memberId) {
        return em.createQuery(
                        "select r from Rental r " +
                                "where r.member.id = :memberId " +
                                "order by r.rentalDate desc", Rental.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<Rental> findByStatusOrderByRentalDateDesc(RentalStatus status) {
        return em.createQuery(
                        "select r from Rental r " +
                                "where r.status = :status " +
                                "order by r.rentalDate desc", Rental.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Rental> findAllOrderByRentalDateDesc() {
        return em.createQuery(
                        "select r from Rental r order by r.rentalDate desc", Rental.class)
                .getResultList();
    }

}
