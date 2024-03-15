package app.HotelExercise;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.Controller.HotelController;
import app.HotelExercise.Controller.RoomController;
import app.HotelExercise.Controller.SecurityController;
import app.HotelExercise.Entities.doesntwork;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;

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

    public EndpointGroup getUserResource() {
        SecurityController securityController = new SecurityController(HibernateConfig.getEntityManagerFactoryConfig("hoteldb", false));
        return () -> {
            path("/user", () -> {
                get("/", securityController.getAllUsers());
                post("/login", securityController.login());
                post("/create", securityController.createUser());
                post("/role", securityController.createRole());
            });

        };
    }

public static EndpointGroup getSecuredRoutes(){
    SecurityController securityController = new SecurityController(HibernateConfig.getEntityManagerFactoryConfig("hoteldb", false));
    return ()->{
        path("/protected", ()->{
            before(securityController.authenticate());
            get("/user_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from USER Protected")),Role.USER);
            get("/admin_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from ADMIN Protected")),Role.ADMIN);
        });
    };
}
public enum Role implements RouteRole { ANYONE, USER, ADMIN }


}
