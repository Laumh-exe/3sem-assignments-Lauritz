package API_Tasks.DTO;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class MovieDTO {
    private boolean adult;
    private String backdrop_path;
    private int id;
    private String title;
    private String original_language;
    private String original_title;
    private String overview;
    private String poster_path;
    private String media_type;
    private int[] genre_ids;
    private double popularity;
    private Date release_date;
    private boolean video;
    private double vote_average;
    private int vote_count;

    public MovieDTO(boolean adult, String backdrop_path, int id, String title, String original_language, String original_title, String overview, String poster_path, String media_type, int[] genre_ids, double popularity, Date release_date, boolean video, double vote_average, int vote_count) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.title = title;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.media_type = media_type;
        this.genre_ids = genre_ids;
        this.popularity = popularity;
        this.release_date = release_date;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }
}
