package The_Point_exercise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PointDAO {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
    private EntityManager em = emf.createEntityManager();

    // Store 1000 Point objects in the database:
    public void store100() {
        em.getTransaction().begin();
        for (int i = 0; i < 1000; i++) {
            Point p = new Point(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();
    }

    // Find the number of Point objects in the database:
    public Object findNumberOfPoints() {
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        return q1.getSingleResult();
    }

    // Find the average X value:
    public double findAverage() {
        Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
        return (double) q2.getSingleResult();
    }


    // Retrieve all the Point objects from the database:
    public List<Point> getAll() {
        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
        return query.getResultList();
    }

    // Close the database connection:
    public void close() {
        em.close();
        emf.close();
    }
}
