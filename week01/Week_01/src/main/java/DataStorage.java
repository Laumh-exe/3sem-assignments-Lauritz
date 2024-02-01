public interface DataStorage<T> {
    String store(T data);
    T retrieve(String source);
}
