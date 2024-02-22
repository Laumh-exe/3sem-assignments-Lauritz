package exercise3.dao;

import exercise3.config.HibernateConfig;
import exercise3.model.Semester;
import exercise3.model.Student;
import exercise3.model.Teacher;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {

    private static StudentDAO studentDAO;
    private static EntityManagerFactory emfTest;
    private Student student1;
    private Student student2;
    private Student student3;
    private Semester semester1;
    private Semester semester2;
    private Semester semester3;
    private Teacher teacher1;
    private Teacher teacher2;

    @BeforeAll
    public static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfig("student_db", true);
        studentDAO = new StudentDAO(true);
    }

    @BeforeEach
    public void setUp() {
        //flush the database
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Student").executeUpdate();
            em.createQuery("DELETE FROM Semester").executeUpdate();
            em.createQuery("DELETE FROM Teacher").executeUpdate();
            em.getTransaction().commit();
        }

        //Setup entities
        student1 = new Student("John", "Doe");
        student2 = new Student("Jane", "Doe");
        student3 = new Student("John", "Smith");

        semester1 = new Semester("Spring 2021", "Spring semester 2021");
        semester2 = new Semester("Fall 2021", "Fall semester 2021");
        semester3 = new Semester("Spring 2022", "Spring semester 2022");

        teacher1 = new Teacher("Hansi", "Hintersser");
        teacher2 = new Teacher("Kenni", "Kern");

        semester1.addStudent(student1);
        semester1.addStudent(student2);
        semester1.addStudent(student3);
        semester2.addStudent(student3);
        semester2.addStudent(student2);
        semester1.addTeacher(teacher1);
        semester2.addTeacher(teacher2);
        semester3.addTeacher(teacher1);

        //Persist entities
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(semester1);
            em.persist(semester2);
            em.getTransaction().commit();
        }
    }

    @Test
    void findAllStudentsByFirstName() {
        //Test method
        assertEquals(2, studentDAO.findAllStudentsByFirstName("John").size());
    }

    @Test
    void findAllStudentsByLastName() {
        //Test method
        assertEquals(2, studentDAO.findAllStudentsByLastName("Doe").size());
    }

    @Test
    void findTotalNumberOfStudentsBySemester() {
        //Test method
        assertEquals(3, studentDAO.findTotalNumberOfStudentsBySemester("Spring 2021"));
    }

    @Test
    void findTotalNumberOfStudentsByTeacher() {
        //Test method
        assertEquals(3, studentDAO.findTotalNumberOfStudentsByTeacher(teacher1));
    }

    @Test
    void findTeacherWithMostSemesters() {
        //Test method
        assertEquals("Hansi", studentDAO.findTeacherWithMostSemesters().getFirstName());
    }

    @Test
    void findSemesterWithFewestStudents() {
        //Test method
        assertEquals("Spring 2022", studentDAO.findSemesterWithFewestStudents().getName());
    }
}