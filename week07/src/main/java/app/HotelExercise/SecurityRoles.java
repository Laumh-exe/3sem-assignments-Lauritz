package app.HotelExercise;

import io.javalin.security.RouteRole;

public enum SecurityRoles implements RouteRole {
    ADMIN, USER, ANYONE
}
