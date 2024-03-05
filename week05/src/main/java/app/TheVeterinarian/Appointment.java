package app.TheVeterinarian;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Appointment {
    private UUID id;
    private LocalDate date;
    private Patient patient;
    private String reason;

    public Appointment(LocalDate date, Patient patient, String reason) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.patient = patient;
        this.reason = reason;
    }
}
