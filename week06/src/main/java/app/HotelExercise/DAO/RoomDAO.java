package app.HotelExercise.DAO;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.Entities.Room;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class RoomDAO extends ADAO<Room, Integer> {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hoteldb", false);

    private static RoomDAO instance;
    private RoomDAO() {}
    public static RoomDAO getRoomDAOInstance() {
        if (instance == null) {
            instance = new RoomDAO();
            ADAO.emf = emf;
        }
        return instance;
    }

    @Override
    public Room getByID(Integer id) {
        try (var em = emf.createEntityManager()) {
            return em.find(Room.class, id);
        }
    }

    @Override
    public void update(Room room) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(room);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<Room> getAll() {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT r FROM Room r", Room.class);
            return query.getResultList();
        }
    }
}
