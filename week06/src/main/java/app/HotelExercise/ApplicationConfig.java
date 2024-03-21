package app.HotelExercise;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import app.HotelExercise.Controller.SecurityController;
import app.HotelExercise.DTO.UserDTO;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.HttpStatus;

/**
 * The ApplicationConfig class is responsible for configuring and managing the application server.
 */
public class ApplicationConfig {
    ObjectMapper om = new ObjectMapper();
    private Javalin app;
    private static ApplicationConfig instance;

    private ApplicationConfig(){};

    /**
     * Returns the singleton instance of the ApplicationConfig class.
     * @return The singleton instance of the ApplicationConfig class.
     */
    public static ApplicationConfig getInstance(){
        if(instance == null){
            instance = new ApplicationConfig();
        }
        return instance;
    }

    

    /**
     * Initializes the application server with default configuration settings.
     * @return The instance of the ApplicationConfig class.
     */
    public ApplicationConfig initiateServer() {
        app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.routing.contextPath = "/api";
        });
        return instance;
    }

    /**
     * Starts the application server on the specified port.
     * @param port The port number on which the server should listen.
     * @return The instance of the ApplicationConfig class.
     */
    public ApplicationConfig startServer(int port) {
        app.start(port);
        return instance;
    }

    /**
     * Sets the route for handling incoming requests.
     * @param route The endpoint group that defines the routes.
     * @param object 
     * @return The instance of the ApplicationConfig class.
     */
    public ApplicationConfig setRoute(EndpointGroup route) {
        app.routes(route);
        return instance;
    }

    /**
     * Sets the exception handling for the application server.
     * @return The instance of the ApplicationConfig class.
     */
    public ApplicationConfig setExceptionHandling() {
        app.exception(Exception.class, (e, ctx) -> {
            ObjectNode node = om.createObjectNode().put("errorMessage", e.getMessage());
            ctx.status(500).json(node);
        });
        return instance;
    }

    public ApplicationConfig errorHandling() {
        app.error(404, ctx -> {
            ctx.json("Custom 404");
        });
        app.error(500, ctx -> {
            ctx.json("Custom 500");
        });
        return instance;
    }

    public void stopServer() {
        app.stop();
    }

    public ApplicationConfig checkSecurityRoles() {
    // Check roles on the user (ctx.attribute("username") and compare with permittedRoles using securityController.authorize()
    app.updateConfig(config -> {

        config.accessManager((handler, ctx, permittedRoles) -> {
            // permitted roles are defined in the last arg to routes: get("/", ctx -> ctx.result("Hello World"), Role.ANYONE);

            Set<String> allowedRoles = permittedRoles.stream().map(role -> role.toString().toUpperCase()).collect(Collectors.toSet());
            if(allowedRoles.contains("ANYONE") || ctx.method().toString().equals("OPTIONS")) {
                // Allow requests from anyone and OPTIONS requests (preflight in CORS)
                handler.handle(ctx);
                return;
            }

            UserDTO user = ctx.attribute("user");
            System.out.println("USER IN CHECK_SEC_ROLES: "+user);
            if(user == null)
                ctx.status(HttpStatus.FORBIDDEN)
                        .json(jsonMapper.createObjectNode()
                                .put("msg","Not authorized. No username were added from the token"));

            if (SecurityController.getInstance().authorize(user, allowedRoles))
                handler.handle(ctx);
            else
                throw new ApiException(HttpStatus.FORBIDDEN.getCode(), "Unauthorized with roles: "+allowedRoles);
        });
    });
    return appConfig;
}
}
