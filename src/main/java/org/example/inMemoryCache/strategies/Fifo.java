package org.example.inMemoryCache.strategies;

import java.util.LinkedList;
import java.util.Queue;

public class Fifo<K, V> implements EvictionPolicy<K, V> {
    private final Queue<K> queue = new LinkedList<>();


    @Override
    public void accessKey(K key) {
        //keep empty return directly from cache class
        //in lru it should do differently
        return;
    }

    @Override
    public void putKey(K key, V value) {
        queue.offer(key);
    }

    @Override
    public K evict() {
        K key = queue.poll();
        return key;
    }
}
