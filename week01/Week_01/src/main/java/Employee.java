import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.util.Date;

public class Employee {
    private String name;
    private LocalDate birthDate;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Month getBithMonth() {
        return this.birthDate.getMonth();
    }

    public int getAge() {
        return Period.between(this.getBirthDate(), LocalDate.now()).getYears();
    }

    public Period getAgePeriod() {
        return Period.between(this.getBirthDate(), LocalDate.now());
    }
}
