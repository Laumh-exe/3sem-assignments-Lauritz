package app.HotelExercise;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.DAO.HotelDAO;
import app.HotelExercise.Entities.Hotel;
import app.HotelExercise.Entities.Room;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hoteldb", false);

        HotelDAO hotelDAO = HotelDAO.getHotelDAOInstance(emf);

        // //SETUP ENTITIES
        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel California");  
        hotel1.setAddress("California Street 123");
        hotel1 = hotelDAO.create(hotel1); // This is so we generate ID
        generateRooms(hotel1);
    
        Hotel hotel2 = new Hotel();
        hotel2.setName("Hilton");
        hotel2.setAddress("Hilton Street 123");
        hotel2 = hotelDAO.create(hotel2);
        generateRooms(hotel2);

        Hotel hotel3 = new Hotel();
        hotel3.setName("Hotel 3");
        hotel3.setAddress("Hotel 3 Street 123");
        hotel3 = hotelDAO.create(hotel3);
        generateRooms(hotel3);
 
        startServer(7070);
    }

    public static void startServer(int port) {
        //JAVALIN SETUP
        Routes routes = new Routes();
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.initiateServer().startServer(port).errorHandling().setRoute(routes.getHotelResource());
    }

    public static void stopServer() {
        ApplicationConfig.getInstance().stopServer();
    }

    public static void generateRooms(Hotel hotel) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hoteldb", false);
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < 5; i++) {  // Floors
            for(int j = 0; j < 20; j++){ // Rooms
                Room room = new Room((i+1)*100+(j+1), 100+((i+1)*10),hotel);
                rooms.add(room);
            }
        }
        HotelDAO.getHotelDAOInstance(emf).addRooms(hotel, rooms);
    }
}
