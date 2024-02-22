package exercise2.dao;

import exercise2.config.HibernateConfig;
import exercise2.model.WasteTruck;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WasteTruckDAOImplTest {
    private static WasteTruckDAOImpl wasteTruckDAO;
    private static EntityManagerFactory emfTest;

    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("recycle_db",true);
        wasteTruckDAO = new WasteTruckDAOImpl(true);
    }

    @Test
    void saveWasteTruck() {
        wasteTruckDAO.saveWasteTruck("BMW", "UK-1024141",6);
    }

    @Test
    void getWasteTruckById() {
    }
}