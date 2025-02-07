package project.libraryManagement.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import project.libraryManagement.domain.Rental;

import java.util.List;

@Repository
public class RentalRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Rental rental){
        em.persist(rental);
    }

    public List<Rental> findAll() {
        return em.createQuery("select m form Rental m", Rental.class).getResultList();
    }

    public List<Rental> findByEmail(String email) {
        return em.createQuery("select m from Rental m", Rental.class)
                .setParameter("email", email)
                .getResultList();
    }
}
