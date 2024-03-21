package app.HotelExercise.DAO;

import app.HotelExercise.Entities.Role;
import app.HotelExercise.Entities.User;
import app.HotelExercise.Exceptions.EntityNotFoundException;

public interface IUserDAO {
    public User createUser(String username, String password);
    public User verifyUser(String username, String password) throws EntityNotFoundException;
    public User addRoleToUser(String username, String role) throws EntityNotFoundException;
    public Role createRole(String role);

    
}
