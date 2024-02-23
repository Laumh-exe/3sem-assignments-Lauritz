package exercise4.dao;

import exercise4.config.HibernateConfig;
import exercise4.model.Package;
import exercise4.model.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ShipmentDAO {
    private EntityManagerFactory emf;

    boolean isTest;
    public ShipmentDAO(boolean isTest) {
        this.isTest = isTest;
        emf = HibernateConfig.getEntityManagerFactoryConfig("gls_tracking_system_v2", isTest);
    }

    public void saveShipment(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(shipment);
            em.getTransaction().commit();
        }
    }

    public void removeShipment(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shipment shipment = retrieveShipmentById(id);
            if (shipment != null) {
                em.remove(shipment);
            }
            em.getTransaction().commit();
        }
    }

    public Shipment retrieveShipmentById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shipment shipment = em.find(Shipment.class, id);
            return shipment;
        }
    }

    public void addPackageToShipment(int shipmentId, int packageId) {
        PackageDAO packageDAO = new PackageDAO(isTest);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shipment shipment = retrieveShipmentById(shipmentId);
            Package pkg = packageDAO.retrievePackageById(packageId);
            if (shipment != null && pkg != null) {
                shipment.addPackage(pkg);
            }
            em.getTransaction().commit();
        }
    }
}
