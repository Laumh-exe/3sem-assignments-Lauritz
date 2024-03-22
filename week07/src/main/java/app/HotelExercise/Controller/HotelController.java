package app.HotelExercise.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.HotelExercise.DAO.HotelDAO;
import app.HotelExercise.DTO.HotelDTO;
import app.HotelExercise.Entities.Hotel;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManagerFactory;

public class HotelController {
    HotelDAO hotelDAO;
    ObjectMapper om = new ObjectMapper();

    public HotelController(EntityManagerFactory emf) {
        hotelDAO = HotelDAO.getHotelDAOInstance(emf);
    }

    public Handler getAllHotels() {
        return ctx -> ctx.json(hotelDAO.getAll());
    }

    public Handler getHotelById() {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(hotelDAO.getByID(id));
        };
    }

    public Handler getRoomsByHotelId() {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(hotelDAO.getByID(id).getRooms());
        };
    }

    public Handler createHotel() {
        return ctx -> {
            Hotel hotel = ctx.bodyAsClass(Hotel.class);
            hotelDAO.create(hotel);
            ctx.status(201);
        };
    }

    public Handler deleteHotel() {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            hotelDAO.delete(hotelDAO.getByID(id));
            ctx.status(204);
        };
    }

    
}
