package app.HotelExercise;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.Controller.HotelController;
import app.HotelExercise.Controller.RoomController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {
    
    public EndpointGroup getHotelResource() {
        HotelController hotelController = new HotelController(HibernateConfig.getEntityManagerFactoryConfig("hoteldb", false));
        RoomController roomController = new RoomController();
        return () -> {
            path("/hotel", () -> {
                get("/", hotelController.getAllHotels());
                get("/{id}", hotelController.getHotelById());
                get("/{id}/rooms", hotelController.getRoomsByHotelId());
                post("/", hotelController.createHotel());
                put("/hotel/{id}", hotelController.createHotel());
                delete("/hotel/{id}", hotelController.deleteHotel());
            });
            path("/room", () -> {
                get("/", roomController.getAllRooms());
                get("/{id}", roomController.getRoomById());
                post("/", roomController.createRoom());
                put("/room/{id}", roomController.createRoom());
                delete("/room/{id}", roomController.deleteRoom());
            });
            
        };      
    }
}
