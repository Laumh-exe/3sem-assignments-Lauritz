package JPA_Lifecycle_and_Annotations;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    private int id;

    @Column(name="firstname", length = 50)
    private String firstname;

    @Column(name="lastname", length = 50)
    private String lastname;

    private int age;

    @Column(name = "email", unique = true)
    private String email;

    public Student() {
    }
}
