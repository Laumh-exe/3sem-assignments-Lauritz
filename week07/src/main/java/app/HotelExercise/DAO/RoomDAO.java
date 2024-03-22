package app.HotelExercise.DAO;

import java.util.List;

import app.HotelExercise.DTO.RoomDTO;
import app.HotelExercise.Entities.Room;
import jakarta.persistence.EntityManagerFactory;

public class RoomDAO extends ADAO<Room, RoomDTO, Integer> {
    private static EntityManagerFactory emf;
    private static RoomDAO instance;
    
    private RoomDAO() {}
    public static RoomDAO getRoomDAOInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new RoomDAO();
            emf = _emf;
            ADAO.emf = _emf;
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
    public List<RoomDTO> getAll() {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT r FROM Room r", Room.class);
            //Turn list of rooms to list of roomDTOs
            return RoomDTO.toRoomDTOList(query.getResultList());
        }
    }
}
