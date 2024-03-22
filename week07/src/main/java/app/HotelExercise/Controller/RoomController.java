package app.HotelExercise.Controller;
import app.HotelExercise.DAO.RoomDAO;
import app.HotelExercise.Entities.Room;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManagerFactory;

public class RoomController {
    RoomDAO roomDAO;

    public RoomController(EntityManagerFactory emf) {
        roomDAO = RoomDAO.getRoomDAOInstance(emf);
    }

    public Handler getAllRooms() {
        var rooms = roomDAO.getAll();
        return ctx -> ctx.json(rooms);
        
    }

    public Handler getRoomById() {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(roomDAO.getByID(id));
        };
    }

    public Handler createRoom() {
        return ctx -> {
            Room room = ctx.bodyAsClass(Room.class);
            roomDAO.create(room);
            ctx.status(201);
        };
    }

    public Handler deleteRoom() {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));

            roomDAO.delete(roomDAO.getByID(id));
            ctx.status(204);
        };
    }
    
}
