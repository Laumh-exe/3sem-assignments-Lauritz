package JPA_Lifecycle_and_Annotations;

import The_Point_exercise.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StudentDAO {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();

    public Student saveStudent(Student student) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(student); // Entity is Managed
            em.getTransaction().commit(); // Entity detached??
            em.close();
            return student;
        }
    }

    public Student updateStudent(Student student) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(student);
            em.getTransaction().commit();
            em.close();
            return student;
        }
    }

    public void deleteStudent(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Student student = readStudent(id);
            if (student != null) {
                em.remove(student);
            }
            em.getTransaction().commit();
        }
    }

    public Student readStudent(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Student newStudent = em.find(Student.class, id);
            em.detach(newStudent);
            return newStudent;
        }
    }

    public List readAllStudents() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery query = em.createQuery("SELECT s FROM Student s", Student.class);
            return query.getResultList();
        }
    }

    public void close() {
        emf.close();
    }

}
