package exercise4.dao;

import exercise4.config.HibernateConfig;
import exercise4.model.Location;
import exercise4.model.Package;
import exercise4.model.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentDAOTest {

    private static ShipmentDAO shipmentDAO;
    private static EntityManagerFactory emfTest;

    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("gls_tracking_system_v2",true);
        shipmentDAO = new ShipmentDAO(true);
    }

    @AfterEach
    void afterEach() {
        //Flush the database before each test
        try(EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Package p");
            query.executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void saveShipment() {
        Shipment shipment = new Shipment(LocalDateTime.now(),new Location(121.23,1231.23,"AddressRoad 28"),new Location(412412.4124,12.993,"DeliveryRoad 69"));
        shipmentDAO.saveShipment(shipment);
        assertEquals(shipment.getId(),shipmentDAO.retrieveShipmentById(shipment.getId()).getId());
    }

    @Test
    void retrieveShipmentById() {
        Shipment shipment = new Shipment(LocalDateTime.now(),new Location(121.23,1231.23,"AddressRoad 28"),new Location(412412.4124,12.993,"DeliveryRoad 69"));
        shipmentDAO.saveShipment(shipment);
        assertEquals(shipment.getId(),shipmentDAO.retrieveShipmentById(shipment.getId()).getId());
    }

}