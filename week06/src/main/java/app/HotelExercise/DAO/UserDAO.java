package app.HotelExercise.DAO;


import app.HotelExercise.ISecurityDAO;
import app.HotelExercise.Entities.Role;
import app.HotelExercise.Entities.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;

public class UserDAO implements ISecurityDAO{

    private static UserDAO instance;
    private static EntityManagerFactory emf;

    private UserDAO() {
    }

    public static UserDAO getUserDAOInstance(EntityManagerFactory emf_) {
        if (instance == null) {
            instance = new UserDAO();
                emf = emf_;
        }
        return instance;
    }

    public List<User> getAllUsers() {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        }
    }

    public User getVerifiedUser(String username, String password) throws IllegalArgumentException {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            User user = query.getSingleResult();
            if (user.verifyPassword(password)) {
                return user;
            }
            throw new IllegalArgumentException("Invalid password");
        } catch (NoResultException e) {
            throw new IllegalArgumentException("Invalid username");
        }
    }

    public User createUser(String username, String password) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = new User(username, password);
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }

    public Role createRole(String role) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Role newRole = new Role(role);
            em.persist(newRole);
            em.getTransaction().commit();
            return newRole;
        }
    }

    public User addUserRole(String username, String role) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, username);
            user.addRole(em.find(Role.class, role));
            em.merge(user);
            em.getTransaction().commit();
            return user;
        }
    }
}
