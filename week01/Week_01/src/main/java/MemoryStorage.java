import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryStorage<T> implements DataStorage<T>{
    private Integer idCounter;
    Map<String, T> memory;

    public MemoryStorage() {
        memory = new HashMap<>();
    }

    @Override
    public String store(T data) {
        idCounter++;
        String id = idCounter.toString();
        memory.put(id,data);
        return id;
    }

    @Override
    public T retrieve(String source) {
        return memory.get(source);
    }
}
