package API_Tasks.Entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MovieCollection {
    List<Movie> collection;

    public MovieCollection() {
        collection = new ArrayList<>();
    }

    public void add(Movie movie) {
        collection.add(movie);
    }

}
