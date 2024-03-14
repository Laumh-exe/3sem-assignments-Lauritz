package app.HotelExercise;

import app.HotelExercise.Entities.Role;
import app.HotelExercise.Entities.User;
import io.javalin.validation.ValidationException;

public interface ISecurityDAO {
    User getVerifiedUser(String username, String password) throws ValidationException;
    User createUser(String username, String password);
    Role createRole(String role);
    User addUserRole(String username, String role);
}