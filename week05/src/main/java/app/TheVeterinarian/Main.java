package app.TheVeterinarian;

import static io.javalin.apibuilder.ApiBuilder.*;

import app.TheVeterinarian.Handlers.AppointmentHandler;
import app.TheVeterinarian.Handlers.PatientHandler;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

public class Main {
    public static void main(String[] args) {
        // JAVALIN SETUP
        Javalin app = Javalin.create(config -> {
            config.routing.contextPath = "/api/vet/";
        }).start(7007);

        // Say hello world at root
        app.get("/", ctx -> ctx.result("Hello World"));

        // ROUTING
        app.routes(getAppointmentResource());
        app.routes(getPatientResource());
    }

    public static EndpointGroup getAppointmentResource() {
        AppointmentHandler appointments = new AppointmentHandler();
        return () -> {
            path("/appointments", () -> {
                get("/", appointments.getAllAppointments());
                get("/{id}", appointments.getAppointmentById());
            });
        };
    }

    public static EndpointGroup getPatientResource() {
        PatientHandler patients = new PatientHandler();
        return () -> {
            path("/patients", () -> {
                get("/", patients.getAllPatients());
                get("/{id}", patients.getPatientById());
            });
        };
    }
}
