package app.HotelExercise.DAO;

import java.util.List;

import javax.swing.text.html.parser.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

public abstract class ADAO<T> implements IDAO<T> {

    protected EntityManagerFactory emf;

    @Override
    public void create(T t) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        }
    }

    @Override
    public void delete(T t) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<T> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            Query query = em.createQuery("SELECT e FROM " + Entity.class.getName() + " e");
            List<T> list = query.getResultList();
            return list;
        }
    }
}
