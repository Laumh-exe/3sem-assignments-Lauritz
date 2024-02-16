package The_Point_exercise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PointDAOTest {

    private static PointDAO pointDAO;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    public static void beforeAll() {
        pointDAO = new PointDAO();
        emf = HibernateConfig.getEntityManagerFactoryConfig();
        em = emf.createEntityManager();
    }

    @BeforeEach
    public void beforeEach() {
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Point p");
        query.executeUpdate();
        em.getTransaction().commit();
    }


    @Test
    public void store1000() {
        pointDAO.store1000();
        assertEquals(1000,pointDAO.getAll().size());
    }

    @Test
    public void findNumberOfPoints() {
        //BEFORE EACH MAKES IT SO WE ALWAYS START AT 0 POINTS
        pointDAO.store1000();
        pointDAO.store1000();
        assertEquals(2000,pointDAO.findNumberOfPoints());
    }

    @Test
    public void findAverageXValue() {
        Point point1 = new Point(10,10);
        Point point2 = new Point(20,10);
        double avg = point1.getX()+point2.getX()/2;
        em.getTransaction().begin();
        em.persist(point1);
        em.persist(point2);
        em.getTransaction().commit();
    }

    @Test
    void getAll() {
    }

    @AfterAll
    public static void close() {
        em.close();
        emf.close();
    }
}