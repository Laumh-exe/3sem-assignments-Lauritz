package GLS_Package_Tracking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PackageDAO {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();

    public Package savePackage(Package pkg) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(pkg);
            em.getTransaction().commit();
            return pkg;
        }
    }

    public void removePackage(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Package pkg = retrievePackageById(id);
            if (pkg != null) {
                em.remove(pkg);
            }
            em.getTransaction().commit();
        }
    }

    public Package retrievePackageById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Package pkg = em.find(Package.class, id);
            return pkg;
        }
    }

    public void updateDeliveryStatus(String trackingNumber, DeliveryStatus deliveryStatus) throws RuntimeException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT p FROM Package p WHERE p.trackingNumber = :number")
                .setParameter("number",trackingNumber);
            Package pkg = (Package) query.getSingleResult();
            if(pkg == null) {
                throw new RuntimeException("Package does not exist");
            }
            pkg.setDeliveryStatus(deliveryStatus);
            em.merge(pkg);
            em.getTransaction().commit();
        }
    }

    public Package retrievePackageByTrackingNumber(String trackingNumber) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT p FROM Package p WHERE p.trackingNumber = :number")
                .setParameter("number",trackingNumber);
            return (Package) query.getSingleResult();
        }
    }

    public List<Package> getAllPackages() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Package> query = em.createQuery("SELECT p FROM Package p", Package.class);
            return query.getResultList();
        }
    }

    public void close() {
        emf.close();
    }
}
