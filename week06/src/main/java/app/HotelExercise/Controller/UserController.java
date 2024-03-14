package app.HotelExercise.Controller;

import app.HotelExercise.DAO.RoomDAO;
import app.HotelExercise.DAO.UserDAO;
import app.HotelExercise.DTO.RoomDTO;
import app.HotelExercise.Entities.Room;
import app.HotelExercise.Entities.User;
import app.HotelExercise.Entities.UserDTO;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManagerFactory;


public class UserController {
    UserDAO userDAO;

    public UserController(EntityManagerFactory emf) {
        userDAO = UserDAO.getUserDAOInstance(emf);

    }

    public Handler login() {
        return ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            ctx.json(userDAO.getVerifiedUser(username, password));
        };
    }

    public Handler createUser() {
        return ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            ctx.json(userDAO.createUser(username, password));
        };
    }

    public Handler createRole() {
        return ctx -> {
            String role = ctx.formParam("role");
            ctx.json(userDAO.createRole(role));
        };
    }

    public Handler getAllUsers() {
        User[] users = userDAO.getAllUsers().toArray(new User[0]);
        return ctx -> ctx.json(users).status(200);
    }
    
}
