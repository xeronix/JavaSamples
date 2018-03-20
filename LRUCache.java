import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache extends LinkedHashMap<Integer, Integer>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2932734112790704287L;
	private int cacheSize;
    
    public LRUCache(int capacity) {

    }
    
    public int get(int key) {
        return super.get(key);
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return this.size() > cacheSize;
    }
    
    public void set(int key, int value) {
    	super.put(key, value);
    }
}