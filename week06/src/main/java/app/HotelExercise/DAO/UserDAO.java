package app.HotelExercise.DAO;


import app.HotelExercise.ISecurityDAO;
import app.HotelExercise.Entities.Role;
import app.HotelExercise.Entities.User;
import io.javalin.validation.ValidationException;

public class UserDAO implements ISecurityDAO{
    public User getVerifiedUser(String username, String password) throws ValidationException {
        return null;
    }

    public User createUser(String username, String password) {
        return null;
    }

    public Role createRole(String role) {
        return null;
    }

    public User addUserRole(String username, String role) {
        return null;
    }
}
