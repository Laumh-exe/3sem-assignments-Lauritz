package API_Tasks.Controllers;

import java.util.List;

public interface IMediaController<E> {
    public List<E> searchCollectionByRating(double rating);
    public List<E> searchCollectionSortedByReleaseDate();
    public void addToCollection(E media);

}
