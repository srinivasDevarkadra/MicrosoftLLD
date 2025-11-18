
package org.example.inMemoryCache;

import org.example.inMemoryCache.strategies.Fifo;

public class CacheClient {

    public static void main(String[] args) {
        // Example: FIFO cache with capacity 2
        Cache<Integer, String> fifo = new Cache<>(new Fifo<>(), 2);
        fifo.put(1, "One");
        fifo.put(2, "Two");
        fifo.put(3, "Three"); // evicts 1 (FIFO)

        System.out.println(fifo.get(2)); // null
    }
}
