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
@Table(name = "semester")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;
    String description;

    @ManyToMany(mappedBy = "semesters", cascade = CascadeType.PERSIST)
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "semester", cascade = CascadeType.PERSIST)
    //wa
    private Set<Student> students = new HashSet<>();

    public Semester(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addTeacher(Teacher teacher) {
        if(teacher != null) {
            teachers.add(teacher);
            if (!teacher.getSemesters().contains(this)) {
                teacher.getSemesters().add(this);
            }
        }
    }

    public void removeTeacher(Teacher teacher) {
        if(teacher != null) {
            teachers.remove(teacher);
            if (teacher.getSemesters().contains(this)) {
                teacher.getSemesters().remove(this);
            }
        }
    }

    public void addStudent(Student student) {
        if(student != null) {
            students.add(student);
            if (student.getSemester() != this) {
                student.setSemester(this);
            }
        }
    }
}
