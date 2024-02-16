package GLS_Package_Tracking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.Lister;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackageDAOTest {

    private static PackageDAO packageDao;
    private static EntityManagerFactory emfTest;

    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig();
        packageDao = new PackageDAO();
    }

    @BeforeEach
    void beforeEach() {
        try(EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Package p");
            query.executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void savePackage() {
        Package pkg = new Package("a1","Lauritz","Alberte");
        packageDao.savePackage(pkg);
        assertEquals(pkg,packageDao.retrievePackageByTrackingNumber(pkg.getTrackingNumber())); //Time variables will always be different, Equals method is therefor modified
    }

    @Test
    void removePackage() {
        try(EntityManager em = emfTest.createEntityManager()) {
            Package pkg = new Package("a1", "Lauritz", "Alberte");
            packageDao.savePackage(pkg);
            packageDao.removePackage(pkg.getId());
            Query query = em.createQuery("SELECT p FROM Package p");
            List<Package> allPackages = query.getResultList();
            assertEquals(0, allPackages.size());
        }
    }

    @Test
    void updateDeliveryStatus() {
        Package pkg = new Package("a1", "Lauritz", "Alberte");
        pkg.setDeliveryStatus(DeliveryStatus.IN_TRANSIT);
        packageDao.savePackage(pkg);
        packageDao.updateDeliveryStatus(pkg.getTrackingNumber(),DeliveryStatus.DELIVERED);
        assertEquals(DeliveryStatus.DELIVERED.toString(),packageDao.retrievePackageByTrackingNumber(pkg.getTrackingNumber()).getDeliveryStatus().toString());
    }

    @Test
    void retrievePackageByTrackingNumber() {
        Package pkg = new Package("a1","Jim","June");
        packageDao.savePackage(pkg);
        assertEquals(pkg,packageDao.retrievePackageByTrackingNumber(pkg.getTrackingNumber()));  //Time variables will always be different, Equals method is therefor modified
    }

    @AfterAll
    static void close() {
        packageDao.close();
    }
}