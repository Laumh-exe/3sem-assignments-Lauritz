package JPQL_Queries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EmployeeDAO {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();

    public Employee saveEmployee(Employee employee) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(employee); // Entity is Managed
            em.getTransaction().commit(); // Entity detached
            return employee;
        }
    }

    public Employee updateEmployee(Employee employee) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(employee);
            em.getTransaction().commit();
            return employee;
        }
    }

    public void deleteEmployee(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Employee employee = readEmployee(id);
            if (employee != null) {
                em.remove(employee);
            }
            em.getTransaction().commit();
        }
    }

    public Employee readEmployee(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Employee employee = em.find(Employee.class, id);
            return employee;
        }
    }

    public List<Employee> readAllEmployees() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
            return query.getResultList();
        }
    }

    public void close() {
        emf.close();
    }
}
