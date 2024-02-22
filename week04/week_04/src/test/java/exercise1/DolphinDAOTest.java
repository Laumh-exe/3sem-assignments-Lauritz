package exercise1;

import Config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DolphinDAOTest {
    private static DolphinDAO dolphinDAO;
    private static EntityManagerFactory emfTest;

    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("dolphin",true);
        dolphinDAO = new DolphinDAO(true);
    }

    @BeforeEach
    void beforeEach() {
        Person p1 = new Person("James");
        p1.addPersonDetail(new PersonDetail("Søborgvej 12",1234,"Helserup",29));
        p1.addNote(new Note("James first Note"));
        p1.addFee(new Fee(300, LocalDate.now()));
        try(EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(p1);
            em.getTransaction().commit();
        }
    }

    @Test
    void createNewPerson() {
        Person expected = new Person("Hanzi");
        PersonDetail pd1 = new PersonDetail("Algade 3", 4300, "Holbæk", 45);
        expected.addPersonDetail(pd1);
        Fee f1 = new Fee(125, LocalDate.of(2023, 8, 25));
        Fee f2 = new Fee(150, LocalDate.of(2023, 7, 19));
        expected.addFee(f1);
        expected.addFee(f2);
        Note note = new Note("Hansi was her");
        expected.addNote(note);
        dolphinDAO.createNewPerson(expected);
        Person actual = dolphinDAO.getPerson(expected.getId());
        assertEquals(actual.getId(),expected.getId());
    }

    @Test
    void printNotesAndNames() {
        dolphinDAO.printNotesAndNames();
    }
}