package exercise2.dao;

import exercise2.config.HibernateConfig;
import exercise2.model.Driver;
import exercise2.model.WasteTruck;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.List;

public class DriverDAOImpl implements IDriverDAO {
    EntityManagerFactory emf;

    public DriverDAOImpl(boolean isTest) {
        emf = HibernateConfig.getEntityManagerFactoryConfig("recycle_db",isTest);
    }
    @Override
    public String saveDriver(String name, String surname, BigDecimal salary) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Driver driver = new Driver(name, salary, surname);
            em.persist(driver);
            em.getTransaction().commit();
            return driver.getId();
        }
    }

    @Override
    public Driver getDriverById(String id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Driver driver = em.find(Driver.class,id);
            em.detach(driver);
            return driver;
        }
    }

    @Override
    public Driver updateDriver(Driver driver) {
        return null;
    }

    @Override
    public void deleteDriver(String id) {

    }

    @Override
    public List<Driver> findAllDriversEmployedAtTheSameYear(String year) {
        return null;
    }

    @Override
    public List<Driver> fetchAllDriversWithSalaryGreaterThan10000() {
        return null;
    }

    @Override
    public double fetchHighestSalary() {
        return 0;
    }

    @Override
    public List<String> fetchFirstNameOfAllDrivers() {
        return null;
    }

    @Override
    public long calculateNumberOfDrivers() {
        return 0;
    }

    @Override
    public Driver fetchDriverWithHighestSalary() {
        return null;
    }
}
