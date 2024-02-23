package exercise4.dao;

import exercise4.config.HibernateConfig;
import exercise4.model.Location;
import exercise4.model.Package;
import exercise4.model.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PackageDAOTest {

    private static PackageDAO packageDao;
    private static EntityManagerFactory emfTest;

    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("gls_tracking_system_v2",true);
        packageDao = new PackageDAO(true);
    }

    @BeforeEach
    void beforeEach() {
        //Flush the database before each test
        try(EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Package p");
            query.executeUpdate();
            em.getTransaction().commit();
        }

    }

    @Test
    void addShipmentToPackage() {
        Shipment shipment = new Shipment(LocalDateTime.now(),new Location(121.23,1231.23,"AddressRoad 28"),new Location(412412.4124,12.993,"DeliveryRoad 69"));
        //Persist the shipment so we can find it again
        try(EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(shipment);
            em.getTransaction().commit();
        }
        Package pkg = new Package("a1","Lauritz","Alberte");
        packageDao.savePackage(pkg);
        packageDao.addShipmentToPackage(pkg.getId(),shipment.getId());
        assertEquals(1,packageDao.retrievePackageById(pkg.getId()).getShipments().size());
    }
}