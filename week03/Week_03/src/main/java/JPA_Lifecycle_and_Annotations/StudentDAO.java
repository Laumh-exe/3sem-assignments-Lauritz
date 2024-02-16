package JPA_Lifecycle_and_Annotations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StudentDAO implements DAO{
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();

    //create
    public Student saveStudent(Object student) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(student); // Entity is Managed
            em.getTransaction().commit(); // Entity detached??
            return (Student) student;
        }
    }

    //Update
    public Student updateEntity(Object student) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(student);
            em.getTransaction().commit();
            return (Student) student;
        }
    }

    //Delete
    public void deleteEntity(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Student student = readEntity(id);
            if (student != null) {
                em.remove(student);
            }
            em.getTransaction().commit();
        }
    }

    //Read
    public Student readEntity(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Student newStudent = em.find(Student.class, id);
            em.detach(newStudent);
            return newStudent;
        }
    }

    public List readAll() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery query = em.createQuery("SELECT s FROM Student s", Student.class);
            return query.getResultList();
        }
    }

    public void close() {
        emf.close();
    }

}
