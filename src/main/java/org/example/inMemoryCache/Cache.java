package org.example.inMemoryCache;

import org.example.inMemoryCache.strategies.EvictionPolicy;

import java.util.HashMap;
import java.util.Map;

public class Cache<K, V> {
    private final EvictionPolicy<K, V> evictionPolicy;
    private final Map<K, V> storage;
    private final int capacity;

    public Cache(EvictionPolicy<K, V> evictionPolicy, int capacity) {
        this.evictionPolicy = evictionPolicy;
        this.capacity = capacity;
        this.storage = new HashMap<>();
    }

    public V get(K key) {
        evictionPolicy.accessKey(key);
        return storage.get(key);
    }

    public void put(K key, V value) {

        if (storage.size() >= capacity) {
            K keyToRemove = evictionPolicy.evict(); //get what key to evict
            if (keyToRemove != null) {
                storage.remove(keyToRemove);
            }
        }

        storage.put(key, value);
        evictionPolicy.putKey(key, value);
    }
}
