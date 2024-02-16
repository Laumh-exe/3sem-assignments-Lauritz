package JPA_Lifecycle_and_Annotations;

import java.util.List;

public interface DAO<E> {
    public E saveStudent(E entity);
    public E updateEntity(E student);
    public void deleteEntity(int id);
    public E readEntity(int id);
    public List readAll();
}
