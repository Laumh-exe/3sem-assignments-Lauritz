package app.HotelExercise;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.DAO.HotelDAO;
import app.HotelExercise.Entities.Hotel;
import app.HotelExercise.Entities.Room;
import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers.*;
import jakarta.persistence.EntityManager;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ObjectNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.util.JSONPObject;

class EndpointTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:7770/api";
        Main.startServer(7770);
    }

    @BeforeEach
    void setupEach() {
        HotelDAO hotelDAO = HotelDAO.getHotelDAOInstance(HibernateConfig.getEntityManagerFactoryConfig("hoteldb", true));

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

    @AfterEach //Remove all entities after each test
    void afterEach() {
        try(EntityManager em = HibernateConfig.getEntityManagerFactoryConfig("hoteldb", true).createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Room").executeUpdate();
            em.createQuery("DELETE FROM Hotel").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @AfterAll
    public static void tearDown() {
        Main.stopServer();
    }

    @Test
    @DisplayName("Test get all hotels")
    void testGetAllHotels() {
        RestAssured.
        when()
        .get("/hotel")
        .then()
        .statusCode(200);
    }

    @Test
    void testGetHotelById() {
        get("/hotel/1")
            .then()
            .assertThat()
            .body("name", equalTo("Hotel California"));
    }

    @Test
    void testGetRoomsByHotelId() {
        get("/hotel/1/rooms")
        .then()
        .assertThat()
        .body("size()", equalTo(100));
    }

    @Test
    void testCreateHotel() {
        given()
            .param("name", "Test Hotel")
            .param("city", "Test City")
            .param("rooms", "[]")
            .when()
            .post("/hotel")
            .then()
            .assertThat()
            .statusCode(201)
            .body("name", equalTo("Test Hotel"));
    }

    @Test
    void testUpdateHotel() {
        given().contentType("application/json").body("{\"name\":\"Test Hotel\",\"city\":\"Test City\",\"rooms\":[]}").when().put("/hotel/1").then().assertThat().statusCode(200);
    }

    @Test
    void testDeleteHotel() {
        delete("/hotel/1").then().assertThat().statusCode(200);
    }

    @Test
    void testGetAllRooms() {
        get("/room").then().assertThat().statusCode(200);
    }

    @Test
    void testGetRoomById() {
        get("/room/1").then().assertThat().statusCode(200);
    }

    @Test
    void testCreateRoom() {
        given().contentType("application/json").body("{\"hotelID\":\"1\",\"number\":1,\"price\":100.0}").when().post("/room").then().assertThat().statusCode(201);
    }

    @Test
    void testUpdateRoom() {
        given().contentType("application/json").body("{\"hotelID\":\"1\",\"number\":1,\"price\":100.0}").when().put("/room/1").then().assertThat().statusCode(200);
    }

    @Test
    void testDeleteRoom() {
        delete("/room/1").then().assertThat().statusCode(200);
    }
}