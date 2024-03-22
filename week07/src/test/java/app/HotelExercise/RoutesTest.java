package app.HotelExercise;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


import org.junit.jupiter.api.*;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.Entities.Hotel;
import app.HotelExercise.Entities.Role;
import app.HotelExercise.Entities.Room;
import app.HotelExercise.Entities.User;
import app.HotelExercise.Exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class RoutesTest {
    private static EntityManagerFactory emf;
    private int hotelId;

    @BeforeAll
    public static void setUp() {
        emf = HibernateConfig.getEntityManagerFactory(true);
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
        
        emf = HibernateConfig.getEntityManagerFactory(true);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Hotel hotel1 = new Hotel("Hotel California", "California Street 123");
            Hotel hotel2 = new Hotel("Hotel New York", "New York Street 123");
            Hotel hotel3 = new Hotel("Hotel Paris", "Paris Street 123");
            em.persist(hotel1);
            em.persist(hotel2);
            em.persist(hotel3);
            hotelId = hotel1.getId();
            List<Room> rooms = generateRooms(hotel1);
            rooms.addAll(generateRooms(hotel2));
            rooms.addAll(generateRooms(hotel3));
            for (Room room : rooms) {
                em.persist(room);
            }
            em.getTransaction().commit();
        }

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Role adminRole = new Role("admin");
            Role userRole = new Role("user");
            em.persist(adminRole);
            em.persist(userRole);
            User user = new User("Henrik", "1234");
            user.addRole(userRole);
            em.persist(user);
            User admin = new User("admin", "admin");
            admin.addRole(adminRole);
            em.persist(admin);
            em.getTransaction().commit();       
        }
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
                .get("/hotel/" + hotelId)
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
    @DisplayName("Test reponse body from /hotel")
    public void testAllHotels() {
        String token = loginAsUser();
        // Use the token in the next request
        String responseBody = RestAssured
                .given()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/hotel")
                .then()
                .statusCode(200)
                .body("size()", equalTo(3))
                .extract()
                .body()
                .asString();
    
        System.out.println(responseBody);
    }
}
