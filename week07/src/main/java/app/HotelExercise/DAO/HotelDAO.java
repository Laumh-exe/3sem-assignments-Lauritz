package app.HotelExercise.DAO;

import java.util.List;
import app.HotelExercise.Entities.Hotel;
import app.HotelExercise.Entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class HotelDAO extends ADAO<Hotel, Integer> {
    private static EntityManagerFactory emf;
    private static HotelDAO instance;

    private HotelDAO() {};

    public static HotelDAO getHotelDAOInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new HotelDAO();
            emf = _emf;
            ADAO.emf = _emf;
        }
        return instance;
    }

    @Override
    public Hotel getByID(Integer id) {
        try (var em = emf.createEntityManager()) {
            return em.find(Hotel.class, id);
        }
    }

    @Override
    public void update(Hotel hotel) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(hotel);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<Hotel> getAll() {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT h FROM Hotel h", Hotel.class);
            return query.getResultList();
        }
    }

    public void addRooms(Hotel hotel, List<Room> rooms) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            hotel = em.merge(hotel); // merge the hotel into the current session
            for (Room room : rooms) {
                room.setHotelID(hotel); // set the hotel for each room
                hotel.addRoom(room); // add the room to the hotel
                em.persist(room); // persist the room
            }
            em.getTransaction().commit();
        }
    }
}
