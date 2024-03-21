package app.HotelExercise;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.DAO.HotelDAO;
import app.HotelExercise.DAO.UserDAO;
import app.HotelExercise.Entities.Hotel;
import app.HotelExercise.Entities.Room;
import app.HotelExercise.Exceptions.EntityNotFoundException;

import static org.hamcrest.Matchers.*;

public class RoutesTest {
    private static EntityManagerFactory emf;

    @BeforeAll
    public static void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        RestAssured.baseURI = "http://localhost:" + Main.getPort() + "/api";
        Main.startServer(emf);
    }

    @AfterAll
    public static void tearDown() {
        Main.closeServer();
        RestAssured.reset();
    }

    @BeforeEach
    public void setupData() throws EntityNotFoundException {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        // //SETUP ENTITIES
        HotelDAO hotelDAO = HotelDAO.getHotelDAOInstance(emf);
        UserDAO userDAO = UserDAO.getUserDAOInstance(emf);
        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel California");
        hotel1.setAddress("California Street 123");
        hotel1 = hotelDAO.create(hotel1); // This is so we generate ID
        hotelDAO.addRooms(hotel1, generateRooms(hotel1));

        Hotel hotel2 = new Hotel();
        hotel2.setName("Hilton");
        hotel2.setAddress("Hilton Street 123");
        hotel2 = hotelDAO.create(hotel2);
        hotelDAO.addRooms(hotel2, generateRooms(hotel2));

        Hotel hotel3 = new Hotel();
        hotel3.setName("Hotel 3");
        hotel3.setAddress("Hotel 3 Street 123");
        hotel3 = hotelDAO.create(hotel3);
        hotelDAO.addRooms(hotel3, generateRooms(hotel3));

        userDAO.createRole("user");
        userDAO.createUser("admin", "admin");
        userDAO.addRoleToUser("admin", "admin");
        userDAO.createUser("Henrik", "1234");

    }

    public List<Room> generateRooms(Hotel hotel) {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < 5; i++) { // Floors
            for (int j = 0; j < 20; j++) { // Rooms
                Room room = new Room((i + 1) * 100 + (j + 1), 100 + ((i + 1) * 10), hotel);
                rooms.add(room);
            }
        }
        return rooms;
    }

    @AfterEach
    public void tearDownData() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.createQuery("DELETE FROM User u").executeUpdate();
            em.createQuery("DELETE FROM Room r").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE public.room_id_seq RESTART WITH 1").executeUpdate();
            em.createQuery("DELETE FROM Hotel h").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE public.hotel_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }

    public void printOutAllData() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            System.out.println("Printing database:");
            System.out.println("ROLES: " + em.createQuery("SELECT r FROM Role r").getResultList());
            System.out.println("USERS: " + em.createQuery("SELECT u FROM User u").getResultList());
            System.out.println("ROOMS: " + em.createQuery("SELECT r FROM Room r").getResultList());
            System.out.println("HOTELS: " + em.createQuery("SELECT h FROM Hotel h").getResultList());
            em.getTransaction().commit();
        }
    }

    private String loginAsUser() {
        // Login as user so next request can be made
        String token = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"Henrik\", \"password\":\"1234\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
        return token;
    }

    private String loginAsAdmin() {
        // Login as admin so next request can be made
        String token = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"admin\", \"password\":\"admin\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
        return token;
    }

    @Test
    @DisplayName("Test reponse body from /hotel/{id}")
    public void testHotelById() {
        String token = loginAsUser();
        // Use the token in the next request
        RestAssured
                .given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/hotel/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Hotel California"))
                .body("address", equalTo("California Street 123"));
    }

    @Test
    @DisplayName("Test reponse body from /hotel/{id}/rooms")
    public void testRoomsByHotelId() {
        String token = loginAsUser();
        // Use the token in the next request
        RestAssured
                .given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/hotel/1/rooms")
                .then()
                .statusCode(200)
                .body("size()", equalTo(100));
    }

    @Test
    @DisplayName("Test reponse body from /room/{id}")
    public void testRoomById() {
        String token = loginAsAdmin();
        // Use the token in the next request
        RestAssured
                .given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/room/1")
                .then()
                .statusCode(200)
                .body("number", equalTo(101))
                .body("price", equalTo(110.0F));
    }

    @Test
    @DisplayName("Test reponse body from /room")
    public void testAllRooms() {
        Response response = RestAssured
                .given()
                .log().all()
                .when()
                .get("/room")
                .then()
                .extract()
                .response();

        assertEquals(200, response.statusCode());
        assertEquals(300, response.jsonPath().getList("$").size());
    }

    @Test
    @DisplayName("Test reponse body from /hotel")
    public void testAllHotels() {
        String token = loginAsUser();
        // Use the token in the next request
        RestAssured
                .given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/hotel")
                .then()
                .statusCode(200)
                .body("size()", equalTo(3));
    }
}
