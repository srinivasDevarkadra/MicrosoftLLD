package org.example.inMemoryCache.strategies;

public interface EvictionPolicy<K, V> {
    public void accessKey(K key);

    public void putKey(K key, V value);

    public K evict();
}
