package app.HotelExercise.DAO;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.Entities.Hotel;
import app.HotelExercise.Main;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HotelDAOTest {

    HotelDAO hotelDAO;

    @BeforeAll
    static void setup() {
        Main.startServer(7770);
    }

    @BeforeEach
    void setupEach() {
        hotelDAO = HotelDAO.getHotelDAOInstance(HibernateConfig.getEntityManagerFactoryConfig("hoteldb", true));

        // //SETUP ENTITIES
        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel California");
        hotel1.setAddress("California Street 123");
        hotel1 = hotelDAO.create(hotel1); // This is so we generate ID
        Main.generateRooms(hotel1);

        Hotel hotel2 = new Hotel();
        hotel2.setName("Hilton");
        hotel2.setAddress("Hilton Street 123");
        hotel2 = hotelDAO.create(hotel2);
        Main.generateRooms(hotel2);

        Hotel hotel3 = new Hotel();
        hotel3.setName("Hotel 3");
        hotel3.setAddress("Hotel 3 Street 123");
        hotel3 = hotelDAO.create(hotel3);
        Main.generateRooms(hotel3);
    }

    @AfterEach
    void afterEach() {
        try(EntityManager em = HibernateConfig.getEntityManagerFactoryConfig("hoteldb", true).createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Room").executeUpdate();
            em.createQuery("DELETE FROM Hotel").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void afterAll() {
        Main.stopServer();
    }

    @Test
    void getByID() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel California");
        hotel.setAddress("California Street 123");
        assertEquals(hotelDAO.getByID(1).getName(), hotel.getName());
    }

    @Test
    void update() {
        Hotel hotel = hotelDAO.getByID(1);
        hotel.setName("Hotel California 2");
        hotelDAO.update(hotel);
        assertEquals(hotelDAO.getByID(1).getName(), "Hotel California 2");
    }

    @Test
    void getAll() {
        assertEquals(hotelDAO.getAll().size(), 3);
        assertEquals(hotelDAO.getAll().get(0).getName(), "Hotel California");
    }
}