package app.HotelExercise.Config;

import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import app.HotelExercise.Controller.SecurityController;
import app.HotelExercise.DTO.UserDTO;
import app.HotelExercise.Exceptions.ApiException;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;

public class ApplicationConfig {
    ObjectMapper om = new ObjectMapper();
    private Javalin app;
    private static ApplicationConfig instance;
    private static SecurityController securityController;

    private ApplicationConfig() {
    };

    public static ApplicationConfig getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            securityController = new SecurityController(emf);
            instance = new ApplicationConfig();
        }
        return instance;
    }

    public static ApplicationConfig getInstance() {
        if (instance == null) {
            throw new RuntimeException("EntityManagerFactory is not set");
        }
        return instance;
    }

    public ApplicationConfig initiateServer() {
        app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.routing.contextPath = "/api";
        });
        return instance;
    }

    public ApplicationConfig startServer(int port) {
        app.start(port);
        return instance;
    }

    public ApplicationConfig setRoute(EndpointGroup route) {
        app.routes(route);
        return instance;
    }

    public ApplicationConfig checkSecurityRoles() {
        app.updateConfig(config -> {
            config.accessManager((handler, ctx, permittedRoles) -> {
                UserDTO user = ctx.sessionAttribute("user");
                Set<String> allowedRoles = permittedRoles.stream().map(role -> role.toString().toUpperCase())
                        .collect(Collectors.toSet());
                if (allowedRoles.contains("ANYONE") || ctx.method().toString().equals("OPTIONS")) {
                    ctx.status(HttpStatus.OK);
                    handler.handle(ctx);
                    return;
                } else {
                    System.out.println("USER IN CHECK_SEC_ROLES: " + user);
                    if (user == null) {
                        ctx.status(HttpStatus.FORBIDDEN)
                                .json(om.createObjectNode()
                                        .put("msg", "Not authorized. No username were added from the token"));
                        return;
                    }
                }

                if (securityController.authorize(user, allowedRoles))
                    handler.handle(ctx);
                else
                    throw new ApiException(HttpStatus.FORBIDDEN.getCode(), "Unauthorized with roles: " + allowedRoles);
            });
        });
        return instance;
    }

    public ApplicationConfig setExceptionHandling() {
        app.exception(Exception.class, (e, ctx) -> {
            System.out.println(ctx.body());
            ObjectNode node = om.createObjectNode().put("errorMessage", e.getMessage());
            ctx.status(500).json(node);
        });
        return instance;
    }

    // public ApplicationConfig errorHandling() {
    // app.error(404, ctx -> {
    // ctx.json("Custom 404");
    // });
    // app.error(500, ctx -> {
    // ctx.json("Custom 500");
    // });
    // return instance;
    // }

    public void stopServer() {
        app.stop();
    }
}
