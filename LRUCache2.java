import java.util.HashMap;
import java.util.LinkedList;

public class LRUCache2 {
	private int size;
	LRUCacheValue value;
	LRUCacheKey key;
	HashMap<LRUCacheKey, LRUCacheValue> cache;
	LinkedList<LRUCacheKey> keyList;
	
	public LRUCache2(int size) {
		this.size = size;
		cache = new HashMap<LRUCacheKey, LRUCacheValue>(size);
		keyList = new LinkedList<>();
	}
	
	public void set(LRUCacheKey key, LRUCacheValue value) {
		if (keyList.size() == size) {
			// cache is full, remove the oldest entry i.e first element from linked list
			LRUCacheKey removedKey = keyList.removeFirst();
			cache.remove(removedKey);
		}
		
		if (!cache.containsKey(key)) {
			keyList.add(key);	
		}
		
		cache.put(key, value);
	}
	
	public LRUCacheValue get(LRUCacheKey key) {
		return cache.get(key);
	}
	
	public void setSize(int size) {
		this.size = size;
	}
}

class LRUCacheKey {
	private int key;
	
	public LRUCacheKey(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}
	
	@Override
	public boolean equals(Object ob) {
		if (!(ob instanceof LRUCacheKey)) {
			return false;
		}
		
		if (ob == this) {
			return true;
		}
		
		LRUCacheKey otherKey = (LRUCacheKey)ob;
		
		return otherKey.getKey() == this.key;
	}
	
	@Override
	public int hashCode() {
		return this.key;
	}
}

class LRUCacheValue {
	private int value;
	
	public LRUCacheValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
