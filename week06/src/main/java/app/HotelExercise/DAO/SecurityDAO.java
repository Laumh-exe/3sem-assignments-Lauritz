package app.HotelExercise.DAO;


import app.HotelExercise.ISecurityDAO;
import app.HotelExercise.Entities.doesntwork;
import app.HotelExercise.Entities.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;

public class SecurityDAO implements ISecurityDAO{

    private static SecurityDAO instance;
    private static EntityManagerFactory emf;

    private SecurityDAO() {
    }

    public static SecurityDAO getUserDAOInstance(EntityManagerFactory emf_) {
        if (instance == null) {
            instance = new SecurityDAO();
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

    public doesntwork createRole(doesntwork role) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            doesntwork newRole = role;
            em.persist(newRole);
            em.getTransaction().commit();
            return newRole;
        }
    }

    public User addUserRole(String username, String role) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, username);
            user.addRole(em.find(doesntwork.class, role));
            em.merge(user);
            em.getTransaction().commit();
            return user;
        }
    }

    @Override
    public doesntwork createRole(String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRole'");
    }
}
