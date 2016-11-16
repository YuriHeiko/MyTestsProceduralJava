import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleLRUCashe<K,V> extends LinkedHashMap<K, V> {
    private final int CAPACITY;

    public SimpleLRUCashe(int CAPACITY) {
        super(CAPACITY + 1, 1.1f, true);
        this.CAPACITY = CAPACITY;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > CAPACITY;
    }
}
