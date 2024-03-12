package app.DogExcercise;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import lombok.Setter;


@Setter
public class dogController {
    Map<Integer, DogDTO> dogs = new HashMap<>();
    
    public dogController() {
        DogDTO.startIdCount();
        //DATA
        DogDTO dog1 = new DogDTO("Fido", 3, "Golden Retriever");
        dog1.setId();
        dogs.put(dog1.getId(), dog1);
        DogDTO dog2 = new DogDTO("Rex", 5, "German Shepherd");
        dog2.setId();
        dogs.put(dog2.getId(), dog2);
        DogDTO dog3 = new DogDTO("Max", 2, "Beagle");
        dog3.setId();
        dogs.put(dog3.getId(), dog3);
    }

    public Handler updateDog() {
        return ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                DogDTO dogDTO = ctx.bodyAsClass(DogDTO.class);
                dogs.put(id, dogDTO);
                ctx.json(dogDTO);
            } catch (IndexOutOfBoundsException e) {
                ctx.status(HttpStatus.NOT_FOUND);
            }
        };
    }

    public Handler deleteDog() {
        return ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                dogs.remove(id);
                ctx.status(HttpStatus.OK);
            } catch (IndexOutOfBoundsException e) {
                ctx.status(HttpStatus.NOT_FOUND);
            }
        };
    }

    public Handler getAllDogs() {
        return ctx -> ctx.json(dogs);
    }

    public Handler getDogById() {
        return ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                DogDTO dog = dogs.get(id);
                    ctx.json(dog);
            } catch (IndexOutOfBoundsException e) {
                ctx.status(HttpStatus.NOT_FOUND);
            }
        };
    }

    public Handler createDog() {
        return ctx -> {
            DogDTO dog = ctx.bodyAsClass(DogDTO.class);
            dog.setId();
            dogs.put(dog.getId(), dog);
            ctx.json(dog).status(HttpStatus.CREATED);
        };
    }
}
