package exercise3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String firstName;
    String lastName;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Semester> semesters = new HashSet<>();

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addSemester(Semester semester) {
        if(semester != null) {
            semesters.add(semester);
            if (!semester.getTeachers().contains(this)) {
                semester.getTeachers().add(this);
            }
        }
    }
}
