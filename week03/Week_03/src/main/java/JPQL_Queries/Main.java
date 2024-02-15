package JPQL_Queries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        EntityManager em = emf.createEntityManager();

        System.out.println("\n All employees: ");

        Query query = em.createQuery("SELECT e FROM Employee e");
        List<Employee> result1 = query.getResultList();
        result1.forEach(employee -> System.out.println(employee.getFirstname()));

        System.out.println("\nEmployees with id higher than 8: ");

        Query query2 = em.createQuery("SELECT e FROM Employee e WHERE e.id > 3");
        List<Employee> result2 = query2.getResultList();
        result2.forEach(employee -> System.out.println(employee.getFirstname() + " : " + employee.getId()));

        System.out.println("\nEmployees with first names that stat with L: ");

        Query query4 = em.createQuery("SELECT e FROM Employee e WHERE e.firstname LIKE 'L%'");
        List<Employee> result4 = query4.getResultList();
        result4.forEach(employee -> System.out.println(employee.getFirstname() + " : " + employee.getDepartment()));

        System.out.println("\nQuery using name parameter: ");

        Query query5 = em.createQuery("SELECT e FROM Employee e WHERE e.firstname = :first");
        query5.setParameter("first", "Lauritz");
        List<Employee> result5 = query5.getResultList();
        result5.forEach(employee -> System.out.println(employee.getFirstname()));

        System.out.println("\nQuery using positional parameter: ");

        Query query6= em.createQuery("SELECT e FROM Employee e WHERE e.firstname = ?1");
        query6.setParameter(1, "John");
        List<Employee> result6 = query6.getResultList();
        result6.forEach(employee -> System.out.println(employee.getFirstname()));

        System.out.println("\nAvarage salary from all employees");

        Query query7= em.createQuery("SELECT AVG(e.salary) FROM Employee e");
        var result7 = query7.getSingleResult();
        System.out.printf("Average: %.2f", result7);
        System.out.println();

        System.out.println("\nTotal salary from all employees");

        Query query8= em.createQuery("SELECT SUM(e.salary) FROM Employee e");
        var result8 = query8.getSingleResult();
        System.out.printf("Total: %.0f", result8);
        System.out.println();

        employeeDAO.close();
    }

}
