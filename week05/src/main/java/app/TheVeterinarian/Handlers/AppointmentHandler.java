package app.TheVeterinarian.Handlers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import app.TheVeterinarian.Appointment;
import app.TheVeterinarian.Patient;
import io.javalin.http.Handler;

public class AppointmentHandler {
    Map<UUID, Appointment> appointments;
    
    public AppointmentHandler() {
        appointments = new HashMap<>();

        //DATA
        Appointment appointment1 = new Appointment(LocalDate.of(2015, 2, 21), new Patient("Fido", "Doggy"), "Annual Checkup");
        Appointment appointment2 = new Appointment(LocalDate.of(2015, 2, 14), new Patient("Fido", "Doggy") , "Annual Checkup"); 
        appointments.put(appointment1.getId(), appointment1);
        appointments.put(appointment2.getId(), appointment2);
    }

    public Handler getAllAppointments() {
        return ctx -> ctx.json("getAllAppointments");
    }

    public Handler getAppointmentById() {
        return ctx -> {
            try {
                UUID id = UUID.fromString(ctx.pathParam("id"));
                ctx.json(appointments.get(id));
            } catch (IndexOutOfBoundsException e) {
                ctx.status(404).result("Appointment not found");
            }
        };
    }
}

