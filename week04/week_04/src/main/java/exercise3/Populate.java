package exercise3;

import exercise3.config.HibernateConfig;
import exercise3.model.Semester;
import exercise3.model.Student;
import exercise3.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populate {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("student_db",false);
        EntityManager em = emf.createEntityManager();

        //SETUP ENTITIES
        Semester semester1 = new Semester("Spring 2021", "Spring semester 2021");
        Semester semester2 = new Semester("Fall 2021", "Fall semester 2021");
        Semester semester3 = new Semester("Spring 2022", "Spring semester 2022");
        Student student1 = new Student("John", "Doe");
        Student student2 = new Student("Jane", "Doe");
        Student student3 = new Student("John", "Smith");
        Student student4 = new Student("Jane", "Smith");
        Teacher teacher1 = new Teacher("Hansi", "Hinterseer");
        Teacher teacher2 = new Teacher("Kenni", "Kern");
        Teacher teacher3 = new Teacher("Foobar", "Baz");
        semester1.addTeacher(teacher1);
        semester1.addTeacher(teacher2);
        semester2.addTeacher(teacher2);
        semester3.addTeacher(teacher3);
        semester1.addStudent(student1);
        semester1.addStudent(student2);
        semester2.addStudent(student3);
        semester3.addStudent(student4);

        //PERSIST ENTITIES
        try {
            em.getTransaction().begin();
            em.persist(semester1);
            em.persist(semester2);
            em.persist(semester3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
