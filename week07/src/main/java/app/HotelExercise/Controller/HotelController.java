package app.HotelExercise.Controller;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.DAO.HotelDAO;
import app.HotelExercise.DTO.HotelDTO;
import app.HotelExercise.Entities.Hotel;
import io.javalin.http.Handler;

public class HotelController {
    HotelDAO hotelDAO = HotelDAO.getHotelDAOInstance(HibernateConfig.getEntityManagerFactory());

    public HotelController() {
        
    }

    public Handler getAllHotels() {
        HotelDTO[] hotels = hotelDAO.getAll().stream().map(HotelDTO::new).toArray(HotelDTO[]::new);
        return ctx -> ctx.json(hotels);
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
