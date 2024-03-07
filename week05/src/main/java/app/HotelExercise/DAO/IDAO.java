package app.HotelExercise.DAO;

import java.util.List;

public interface IDAO<T> {
    public void create(T t);
    public List<T> getAll();
    public T getByID(T t);
    public void update(T t);
    public void delete(T t);
}
