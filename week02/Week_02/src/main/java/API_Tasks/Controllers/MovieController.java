package API_Tasks.Controllers;

import API_Tasks.DTO.MovieDTO;
import API_Tasks.Entities.Movie;
import API_Tasks.Entities.MovieCollection;
import API_Tasks.Mappers.MovieMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieController<E> implements IMediaController{
    MovieMapper movieMapper;
    MovieCollection movieCollection;

    public MovieController() {
        movieMapper = new MovieMapper();
        movieCollection = new MovieCollection();
    }

    /**
     * Returns all movies above rating
     * @param rating
     * @return
     */
    @Override
    public List<Movie> searchCollectionByRating(double rating) {
        List<Movie> movies = movieCollection.getCollection()
            .stream()
            .filter(movie -> movie.getRating() > rating)
            .collect(Collectors.toList());
        if (movies.size() >= 0) {
            System.out.println("No movies found");
        }
        return movies;
    }

    public void printCollection() {
        movieCollection.getCollection().stream().forEach(movie -> System.out.println(movie.getTitle()));
    }

    public void printRating() {
        movieCollection.getCollection().stream().forEach(movie -> System.out.println(movie.getTitle()+ " : " + movie.getRating()));
    }

    @Override
    public List<Movie> searchCollectionSortedByReleaseDate() {
        List<Movie> movies = movieCollection.getCollection()
            .stream()
            .sorted(Comparator.comparing(movie -> movie.getReleaseYear()))
            .collect(Collectors.toList());
        if (movies.size() >= 0) {
            System.out.println("No movies found");
        }
        return movies;
    }

    @Override
    public void addToCollection(Object movie) throws IllegalArgumentException{
        if(!(movie instanceof Movie)) throw new IllegalArgumentException("movie argument has to be instance of Movie");
        movieCollection.add((Movie) movie);
    }

    public Movie getNewMovieByID(String id) {
        MovieDTO movieDTO = movieMapper.getByID(id);
        Movie movie = new Movie(movieDTO.getTitle(), movieDTO.getRelease_date(), movieDTO.getVote_average());
        return movie;
    }
}
