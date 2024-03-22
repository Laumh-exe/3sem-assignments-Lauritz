package app.HotelExercise;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.HotelExercise.Controller.HotelController;
import app.HotelExercise.Controller.RoomController;
import app.HotelExercise.Controller.SecurityController;

public class Routes {
    private static HotelController hotelController;
    private static RoomController roomController;
    private static SecurityController securityController;
    private static ObjectMapper om = new ObjectMapper();

    public Routes(EntityManagerFactory emf) {
        hotelController = new HotelController(emf);
        roomController = new RoomController(emf);
        securityController = new SecurityController(emf);
    }
    
    public EndpointGroup getHotelResource() {
        return () -> {
            before(securityController.authenticate());
            path("/hotel", () -> {
                get("/", hotelController.getAllHotels(), SecurityRoles.USER );
                get("/{id}", hotelController.getHotelById(), SecurityRoles.USER );
                get("/{id}/rooms", hotelController.getRoomsByHotelId(), SecurityRoles.USER);
                post("/", hotelController.createHotel(), SecurityRoles.ADMIN);
                put("/hotel/{id}", hotelController.createHotel(), SecurityRoles.ADMIN);
                delete("/hotel/{id}", hotelController.deleteHotel(), SecurityRoles.ADMIN);
            });
            before(securityController.authenticate());
            path("/room", () -> {
                get("/", roomController.getAllRooms(), SecurityRoles.ANYONE); //THIS IS TO TEST WITHOUT USER LOGGED IN
                get("/{id}", roomController.getRoomById(), SecurityRoles.ADMIN); //THIS IS TO TEST WITH ADMIN LOGGED IN
                post("/", roomController.createRoom(), SecurityRoles.ADMIN);
                put("/room/{id}", roomController.createRoom(), SecurityRoles.ADMIN);
                delete("/room/{id}", roomController.deleteRoom(), SecurityRoles.ADMIN);
            });
            
        };      
    }

    public EndpointGroup securityRoutes() {
        return ()->{
            path("/auth", ()->{
                post("/login", securityController.login(),SecurityRoles.ANYONE);
                post("/register", securityController.register(),SecurityRoles.ANYONE);
            });
        };
    }

    public EndpointGroup securedRoutes(){
        return ()->{
            path("/protected", ()->{
                before(securityController.authenticate());
                get("/user_demo",(ctx)->ctx.json(om.createObjectNode().put("msg",  "Hello from USER Protected")),SecurityRoles.USER);
                get("/admin_demo",(ctx)->ctx.json(om.createObjectNode().put("msg",  "Hello from ADMIN Protected")),SecurityRoles.ADMIN);
            });
        };
    }
}
