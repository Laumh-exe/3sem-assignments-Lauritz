package JPQL_Queries;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private int id;

    @Column(name="firstname", length = 50)
    private String firstname;

    @Column(name="lastname", length = 50)
    private String lastname;

    private double salary;

    private String department;

    @Column(name = "email", unique = true)
    private String email;

    public Employee() {
    }

    public Employee(String firstname, String lastname, String department, double salary, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.department = department;
        this.salary = salary;
        this.email = email;
    }

    @PrePersist
    public void verifyEmailPrePersist() throws RuntimeException {
        if (!this.email.contains("@")) {
            throw new RuntimeException("Email has to contain @");
        }
    }

    @PreUpdate
    public void verifyEmailPreUpdate() throws RuntimeException {
        if (!this.email.contains("@")) {
            throw new RuntimeException("Email has to contain @");
        }
    }
}
