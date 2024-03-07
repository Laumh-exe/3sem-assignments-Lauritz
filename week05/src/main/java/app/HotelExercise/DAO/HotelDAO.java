package app.HotelExercise.DAO;

import app.HotelExercise.Config.HibernateConfig;
import app.HotelExercise.Entities.Hotel;
import jakarta.persistence.EntityManagerFactory;

public class HotelDAO extends ADAO<Hotel>{

    private static EntityManagerFactory emf;   
    private static HotelDAO instance;
    private HotelDAO() {};
    public static HotelDAO getInstance() {
        if (instance == null) {
            instance = new HotelDAO();
            emf = HibernateConfig.getEntityManagerFactoryConfig("hoteldb", false);
        }
        return instance;
    }

    @Override
    public Hotel getByID(Hotel hotel) {
        try(var em = emf.createEntityManager()) {
            return em.find(Hotel.class, hotel.getId());
        }
    }

    @Override
    public void update(Hotel hotel) {
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(hotel);
            em.getTransaction().commit();
        }
    }


}
