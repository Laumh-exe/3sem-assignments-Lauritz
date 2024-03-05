package app.DogExcercise;

import java.util.ArrayList;
import java.util.List;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    public static void main(String[] args) {
        //JAVALIN SETUP
        Javalin app = Javalin.create().start(7070);

        //DATA
        dogController dogs = new dogController();
        dogs.addDog(new Dog("Fido", 3, "Golden Retriever"));
        dogs.addDog(new Dog("Rex", 5, "German Shepherd"));
        dogs.addDog(new Dog("Max", 2, "Beagle"));

        //ROUTING
        app.get("/", ctx -> ctx.result("Hello World"));
        app.routes(getDogResource());
    }

    private static EndpointGroup getDogResource() {
        dogController dogs = new dogController();
        return () -> {
            path("/dogs", ()-> {
                get
            })
        };     
    }
}