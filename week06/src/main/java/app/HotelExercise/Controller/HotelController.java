package app.HotelExercise.Controller;

import app.HotelExercise.DAO.HotelDAO;
import app.HotelExercise.DTO.HotelDTO;
import app.HotelExercise.Entities.Hotel;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManagerFactory;

public class HotelController {
    HotelDAO hotelDAO;

    public HotelController(EntityManagerFactory emf) {
        hotelDAO = HotelDAO.getHotelDAOInstance(emf);
    }

    public Handler getAllHotels() {
        HotelDTO[] hotels = hotelDAO.getAll().stream().map(HotelDTO::new).toArray(HotelDTO[]::new);
        return ctx -> ctx.json(hotels).status(200);
    }

    public Handler getHotelById() {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(new HotelDTO(hotelDAO.getByID(id)));
        };
    }

    public Handler getRoomsByHotelId() {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(hotelDAO.getByID(id).getRoomsAsDTO());
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
