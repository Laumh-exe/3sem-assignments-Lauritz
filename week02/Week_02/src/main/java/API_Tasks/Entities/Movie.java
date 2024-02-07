package API_Tasks.Entities;

import lombok.Getter;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Getter
public class Movie {
    String title;
    int releaseYear;
    double rating;


    public Movie(String title, Date releaseDate, double rating) {
        this.title = title;
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(releaseDate);
        this.releaseYear = cal.get(Calendar.YEAR);
        this.rating = rating;
    }
}
