import java.time.Year;

public class Book {
    private String title;
    private String Author;
    private Year publicationYear;
    private int pages;
    private int rating;

    public Book(String title, String author, Year publicationYear, int pages, int rating) {
        this.title = title;
        Author = author;
        this.publicationYear = publicationYear;
        this.pages = pages;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return Author;
    }

    public Year getPublicationYear() {
        return publicationYear;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "Book{" +
            "title='" + title + '\'' +
            ", Author='" + Author + '\'' +
            ", publicationYear=" + publicationYear +
            ", pages=" + pages +
            ", rating=" + rating +
            '}';
    }

    public int getRating() {
        return rating;
    }
}
