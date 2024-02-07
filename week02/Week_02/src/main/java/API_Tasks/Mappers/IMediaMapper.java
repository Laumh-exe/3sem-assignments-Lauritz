package API_Tasks.Mappers;

public interface IMediaMapper<E> {
    public String getResponseBody(String url);
    public E getByID(String id);
}
