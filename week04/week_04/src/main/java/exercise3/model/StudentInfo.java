package exercise3.model;

import exercise3.model.Student;
import exercise3.model.Teacher;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;

@Getter
public class StudentInfo{
    String fullName;
    int studentId;
    String thisSemesterName;
    String thisSemesterDescription;

    public StudentInfo(String firstName, String lastName, int studentId, String thisSemesterName, String thisSemesterDescription) {
        this.fullName = firstName + " " + lastName;
        this.studentId = studentId;
        this.thisSemesterName = thisSemesterName;
        this.thisSemesterDescription = thisSemesterDescription;
    }
}