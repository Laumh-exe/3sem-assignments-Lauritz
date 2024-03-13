package app.HotelExercise.Controller;

import app.HotelExercise.DAO.RoomDAO;
import app.HotelExercise.DTO.RoomDTO;
import app.HotelExercise.Entities.Room;
import io.javalin.http.Handler;

public class RoomController {
    RoomDAO roomDAO = RoomDAO.getRoomDAOInstance();

    public Handler getAllRooms() {
        RoomDTO[] rooms = roomDAO.getAll().stream().map(RoomDTO::new).toArray(RoomDTO[]::new);
        return ctx -> ctx.json(rooms);
        
    }

    public Handler getRoomById() {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(new RoomDTO(roomDAO.getByID(id)));
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
