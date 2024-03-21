package app.HotelExercise;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

import java.util.ArrayList;
import java.util.List;

import app.HotelExercise.Config.ApplicationConfig;
import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.Controller.SecurityController;
import app.HotelExercise.DAO.HotelDAO;
import app.HotelExercise.DAO.UserDAO;
import app.HotelExercise.Entities.Hotel;
import app.HotelExercise.Entities.Room;
import app.HotelExercise.Exceptions.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    private static final int port = 7170;
    private static DemoController demoController = new DemoController();

    public static void main(String[] args) throws EntityNotFoundException {
        removeAll();
        setupEntities(HibernateConfig.getEntityManagerFactory());

        // JAVALIN SETUP
        startServer(HibernateConfig.getEntityManagerFactory());
    }

    public static void removeAll() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
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

    public static int getPort() {
        return port;
    }

    public static void startServer(EntityManagerFactory emf) {
        SecurityController securityController = new SecurityController(emf);
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance(emf);
        applicationConfig
                .initiateServer()
                .startServer(port)
                .setExceptionHandling()
                .setRoute(Routes.getHotelResource(securityController))
                .setRoute(Routes.securityRoutes(securityController))
                .setRoute(Routes.securedRoutes(securityController))
                .checkSecurityRoles()
                .setRoute(() -> {
                    path("/demo", () -> {
                        get("/test", demoController.sayHello());
                        get("/error", ctx -> {
                            throw new Exception("Dette er en test");
                        });
                    });
                });
    }

    public static void setupEntities(EntityManagerFactory emf) throws EntityNotFoundException {
        // //SETUP ENTITIES
        HotelDAO hotelDAO = HotelDAO.getHotelDAOInstance(emf);
        UserDAO userDAO = UserDAO.getUserDAOInstance(emf);
        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel California");
        hotel1.setAddress("California Street 123");
        hotel1 = hotelDAO.create(hotel1); // This is so we generate ID
        hotelDAO.addRooms(hotel1,generateRooms(hotel1));

        Hotel hotel2 = new Hotel();
        hotel2.setName("Hilton");
        hotel2.setAddress("Hilton Street 123");
        hotel2 = hotelDAO.create(hotel2);
        hotelDAO.addRooms(hotel2,generateRooms(hotel2));

        Hotel hotel3 = new Hotel();
        hotel3.setName("Hotel 3");
        hotel3.setAddress("Hotel 3 Street 123");
        hotel3 = hotelDAO.create(hotel3);
        hotelDAO.addRooms(hotel3,generateRooms(hotel3));


        userDAO.createRole("user");
        userDAO.createUser("admin","admin");
        userDAO.addRoleToUser("admin", "admin");  
        userDAO.createUser("Henrik","1234");
    }

    public static List<Room> generateRooms(Hotel hotel) {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < 5; i++) { // Floors
            for (int j = 0; j < 20; j++) { // Rooms
                Room room = new Room((i + 1) * 100 + (j + 1), 100 + ((i + 1) * 10), hotel);
                rooms.add(room);
            }
        }
        return rooms;
    }

    public static void closeServer() {
        ApplicationConfig.getInstance().stopServer();
    }
}
