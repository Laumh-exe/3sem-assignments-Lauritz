package API_Tasks;

import API_Tasks.Controllers.MovieController;
import API_Tasks.Entities.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Main {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String apiKey = "e8e73ebdf19040fa1383f852124db38d";
    public static void main(String[] args) {
        //ADD MOVIES TO COLLECTION
        MovieController movieController = new MovieController();
        movieController.addToCollection(movieController.getNewMovieByID("tt1034415")); //SUSPIRIA
        movieController.addToCollection(movieController.getNewMovieByID("tt0111161")); //SHAWSHANK
        movieController.addToCollection(movieController.getNewMovieByID("tt0068646")); //GODFATHER
        movieController.addToCollection(movieController.getNewMovieByID("tt0468569")); //THEDARKKNIGHT
        movieController.addToCollection(movieController.getNewMovieByID("tt0071562")); //GODFATHER PT2
        movieController.addToCollection(movieController.getNewMovieByID("tt0050083")); //12 ANGRY MEN
        movieController.addToCollection(movieController.getNewMovieByID("tt0110912")); //PULP FICTION
        movieController.addToCollection(movieController.getNewMovieByID("tt0108052")); //SCHINDLERS LIST
        movieController.addToCollection(movieController.getNewMovieByID("tt0167260")); //LOTR RETURN OF THE KING
        movieController.addToCollection(movieController.getNewMovieByID("tt0060196")); //THE GOOD THE BAD AND THE UGLY
        movieController.addToCollection(movieController.getNewMovieByID("tt0109830")); //FORREST GUMP
        movieController.addToCollection(movieController.getNewMovieByID("tt0167261")); //LOTR THE TWO TOWERS
        movieController.addToCollection(movieController.getNewMovieByID("tt0120737")); //LOTR THE FELLOWSHIP OF THE RING
        movieController.addToCollection(movieController.getNewMovieByID("tt1375666")); //INCEPTION

        //Print collection
        //movieController.printCollection();
        movieController.printRating();

        //SEARCH COLLECTION
        List<Movie> result = movieController.searchCollectionByRating(8.5);
        System.out.println("\nVote average over 8.5:");
        result.stream().forEach(movie -> System.out.println(movie.getTitle() + " : " + movie.getRating()));

        //SORT COLLECTION BY RELEASEDATE
        List<Movie> result2 = movieController.searchCollectionSortedByReleaseDate();
        System.out.println("\nMovies sorted by release date:");
        result.forEach(movie -> System.out.println(movie.getTitle() + " : " + movie.getReleaseYear()));

    }
}
