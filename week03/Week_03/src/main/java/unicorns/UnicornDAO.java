package unicorns;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UnicornDAO {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();


    public Unicorn save(Unicorn unicorn) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(unicorn);
        em.getTransaction().commit();
        em.close();
        return unicorn;
    }

    public Unicorn update(Unicorn unicorn) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(unicorn);
        em.getTransaction().commit();
        em.close();
        return unicorn;
    }

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Unicorn unicorn = findById(id);
        if (unicorn != null) {
            em.remove(unicorn);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Unicorn findById(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Unicorn newUnicorn = em.find(Unicorn.class, id);
        em.detach(newUnicorn);
        return newUnicorn;
    }

    public void close() {
        emf.close();
    }

}
