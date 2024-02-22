package exercise2.dao;

import exercise2.config.HibernateConfig;
import exercise2.model.Driver;
import exercise2.model.WasteTruck;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class WasteTruckDAOImpl implements IWasteTruckDAO{
    EntityManagerFactory emf;


    public WasteTruckDAOImpl(boolean isTest) {
        emf = HibernateConfig.getEntityManagerFactoryConfig("recycle_db",isTest);
    }

    @Override
    public void saveWasteTruck(String brand, String registrationNumber, int capacity) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(new WasteTruck(brand, registrationNumber, capacity));
            em.getTransaction().commit();
        }
    }

    @Override
    public WasteTruck getWasteTruckById(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            WasteTruck truck = em.find(WasteTruck.class,id);
            em.detach(truck);
            return truck;
        }
    }

    @Override
    public void setWasteTruckAvailable(WasteTruck wasteTruck, boolean available) {
        try(EntityManager em = emf.createEntityManager()) {
            wasteTruck.setAvailable(true);
            em.getTransaction().begin();
            em.merge(wasteTruck);
            em.getTransaction().commit();
        }
    }

    @Override
    public void deleteWasteTruck(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            WasteTruck truck = em.find(WasteTruck.class,id);
            em.remove(truck);
            em.getTransaction().commit();
        }
    }

    @Override
    public void addDriverToWasteTruck(WasteTruck wasteTruck, Driver driver) {
        try(EntityManager em = emf.createEntityManager()) {
            driver.setTruck(wasteTruck);
            em.getTransaction().begin();
            em.merge(driver);
            em.getTransaction().commit();
        }
    }

    @Override
    public void removeDriverFromWasteTruck(WasteTruck wasteTruck, String id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Driver driver = em.find(Driver.class, id);
            if(driver.getTruck().equals(wasteTruck)) {
                driver.setTruck(null);
            }
            em.merge(driver);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<WasteTruck> getAllAvailableTrucks() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<WasteTruck> query = em.createQuery("SELECT t FROM WasteTruck t", WasteTruck.class);
            List<WasteTruck> trucks = query.getResultList();
            return trucks;
        }
    }
}
