package JPA_Lifecycle_and_Annotations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class StudentDAO {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();


    public Student saveStudent(Student student) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
        em.close();
        return student;
    }

    public Student updateStudent(Student student) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(student);
        em.getTransaction().commit();
        em.close();
        return student;
    }

    public void deleteStudent(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student = readStudent(id);
        if (student != null) {
            em.remove(student);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Student readStudent(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student newStudent = em.find(Student.class, id);
        em.detach(newStudent);
        return newStudent;
    }

    public List readAllStudents() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT s FROM student")
            .getResultList();
    }

    public void close() {
        emf.close();
    }

}
