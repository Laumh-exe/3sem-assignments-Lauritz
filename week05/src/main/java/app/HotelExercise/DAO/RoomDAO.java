package app.HotelExercise.DAO;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.Entities.Room;
import jakarta.persistence.EntityManagerFactory;

public class RoomDAO extends ADAO<Room> {

    private static EntityManagerFactory emf;

    private static RoomDAO instance;
    private RoomDAO() {}
    public static RoomDAO getInstance() {
        if (instance == null) {
            instance = new RoomDAO();
            emf = HibernateConfig.getEntityManagerFactoryConfig("hoteldb", false);
        }
        return instance;
    }

    @Override
    public Room getByID(Room room) {
        try (var em = emf.createEntityManager()) {
            return em.find(Room.class, room.getId());
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
}
