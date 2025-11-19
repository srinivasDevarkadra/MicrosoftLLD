package org.example.inMemoryCache;

import org.example.inMemoryCache.strategies.EvictionPolicy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache<K, V> {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final EvictionPolicy<K, V> evictionPolicy;
    private final Map<K, V> storage;
    private final int capacity;

    public Cache(EvictionPolicy<K, V> evictionPolicy, int capacity) {
        this.evictionPolicy = evictionPolicy;
        this.capacity = capacity;
        this.storage = new HashMap<>();
    }

    public V get(K key) {
        lock.readLock().lock(); // 🔹 Multiple readers allowed
        try {
            V value = storage.get(key);
            if (value == null) {
                System.out.println("Key not found by " + Thread.currentThread().getName());
                return null;
            }
            evictionPolicy.accessKey(key);
            return value;

        } finally {
            lock.readLock().unlock();
        }
    }

    public void put(K key, V value) {

        lock.writeLock().lock(); // 🔸 Only ONE writer allowed
        try {
            if (storage.size() >= capacity) {
                K keyToRemove = evictionPolicy.evict(); //get what key to evict
                if (keyToRemove != null) {
                    storage.remove(keyToRemove);
                }
            }

            storage.put(key, value);
            evictionPolicy.putKey(key, value);
        } finally {
            lock.writeLock().unlock();
        }


    }
}
