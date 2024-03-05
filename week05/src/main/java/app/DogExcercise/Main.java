package app.DogExcercise;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    public static void main(String[] args) {


        //JAVALIN SETUP
        Javalin app = Javalin.create(config -> {
            config.routing.contextPath = "/api/";
        }).start(7070);

        //ROUTING
        app.routes(getDogResource());
    }

    private static EndpointGroup getDogResource() {
        dogController dogs = new dogController();
        return () -> {
            path("/dogs", ()-> {
                get("/", dogs.getAllDogs());
                get("/{id}", dogs.getDogById());
                post("/",dogs.createDog());
                put("/dog/{id}",dogs.updateDog());
                delete("/dog/{id}", dogs.deleteDog());
            });
        };     
    }
}