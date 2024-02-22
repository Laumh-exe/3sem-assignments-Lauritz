package exercise2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "driver")
public class Driver {
    @Id
    private String id;

    @Temporal(TemporalType.DATE)
    @Column(name = "employment_date")
    private LocalDate employmentDate;

    private String name;

    private BigDecimal salary;

    private String surname;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private WasteTruck truck;

    public Driver(String name, BigDecimal salary, String surname) {
        this.name = name;
        this.salary = salary;
        this.surname = surname;
    }

    @PrePersist
    private void prePersist() {
        generateEmploymentDate();
        generateId();
    }

    private void generateId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        String id = "";
        id += this.employmentDate.format(formatter) + "-";
        id += name.substring(0,1).toUpperCase();
        id += surname.substring(0,1).toUpperCase()+ "-";
        Random rnd = new Random();
        id += rnd.nextInt(100,999);
        id += surname.substring(surname.length()-1).toUpperCase();
        if(!validateDriverId(id)) {
            throw new RuntimeException("ID: "+ id + " CANNOT BE VALIDATED");
        }
        this.id = id;
    }

    private void generateEmploymentDate(){
        this.employmentDate = LocalDate.now();
    }

    public Boolean validateDriverId(String driverId) {
        return driverId.matches("[0-9][0-9][0-9][0-9][0-9][0-9]-[A-Z][A-Z]-[0-9][0-9][0-9][A-Z]");
    }
}