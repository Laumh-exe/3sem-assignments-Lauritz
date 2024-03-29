package app.TheVeterinarian.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import app.TheVeterinarian.Patient;
import io.javalin.http.Handler;
import lombok.Getter;

@Getter
public class PatientController {
    protected Map<UUID, Patient> patients = new HashMap<>();

    public PatientController() {
        Patient patient1 = new Patient("Fido", "Dog");
        patient1.addAllergy("Peanuts");
        patient1.addMedication("Advil");
        Patient patient2 = new Patient("Whiskers", "Cat");
        patient2.addAllergy("Lactose");
        patient2.addMedication("Tylenol");
        patients.put(patient1.getId(), patient1);
        patients.put(patient2.getId(), patient2);
    }

    public Handler getAllPatients() {
        return ctx -> ctx.json(patients).status(200);
    }

    public Handler getPatientById() {
        try {
            return ctx -> {
                UUID id = UUID.fromString(ctx.pathParam("id"));
                ctx.json(patients.get(id)).status(200);
            };
        } catch (IllegalArgumentException | NullPointerException e) {
            return ctx -> ctx.status(404).result("Patient not found");
        }
    }
}
