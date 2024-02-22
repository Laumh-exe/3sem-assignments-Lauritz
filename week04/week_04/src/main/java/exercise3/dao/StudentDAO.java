package exercise3.dao;

import exercise3.config.HibernateConfig;
import exercise3.model.StudentInfo;
import exercise3.model.Semester;
import exercise3.model.Student;
import exercise3.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class StudentDAO implements IStudentDAO{

    EntityManagerFactory emf;

    public StudentDAO(boolean isTrue) {
        emf = HibernateConfig.getEntityManagerFactoryConfig("student_db",isTrue);
    }

    @Override
    public List<Student> findAllStudentsByFirstName(String firstName) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Student s WHERE s.firstName = :firstName", Student.class)
                    .setParameter("firstName", firstName)
                    .getResultList();
        }
    }

    @Override
    public List<Student> findAllStudentsByLastName(String lastName) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Student s WHERE s.lastName = :lastName", Student.class)
                    .setParameter("lastName", lastName)
                    .getResultList();
        }
    }

    @Override
    public long findTotalNumberOfStudentsBySemester(String semesterName) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT COUNT(s) FROM Student s JOIN s.semester sem WHERE sem.name = :semesterName", Long.class)
                    .setParameter("semesterName", semesterName)
                    .getSingleResult();
        }
    }

    @Override
    public long findTotalNumberOfStudentsByTeacher(Teacher teacher) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT COUNT(s) FROM Student s JOIN s.semester sem JOIN sem.teachers t WHERE t.id = :teacherId", Long.class)
                    .setParameter("teacherId", teacher.getId())
                    .getSingleResult();
        }
    }

    @Override
    public Teacher findTeacherWithMostSemesters() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT t FROM Teacher t JOIN t.semesters sem GROUP BY t.id ORDER BY COUNT(sem) DESC", Teacher.class)
                    .setMaxResults(1)
                    .getSingleResult();
        }
    }

    @Override
    public Semester findSemesterWithFewestStudents() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT sem FROM Semester sem JOIN sem.students s GROUP BY sem.id ORDER BY COUNT(s) ASC", Semester.class)
                    .setMaxResults(1)
                    .getSingleResult();
        }
    }

    @Override
    public StudentInfo getAllStudentInfo(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT new exercise3.model.StudentInfo(s.firstName, s.lastName, s.id, sem.name, sem.description) FROM Student s JOIN s.semester sem WHERE s.id = :id", StudentInfo.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }
}
