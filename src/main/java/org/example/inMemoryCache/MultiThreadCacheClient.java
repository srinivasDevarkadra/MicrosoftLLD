package org.example.inMemoryCache;

import org.example.inMemoryCache.strategies.Fifo;

public class MultiThreadCacheClient {

    public  static void main(String[] args) throws InterruptedException {
        Cache<Integer, String> cache = new Cache<>(new Fifo<>(), 3);
        Thread t1 = new Thread(() -> cache.put(1, "srini"), "thread1");
        Thread t2 = new Thread(() -> cache.put(2, "shekar"), "thread2");
        Thread t3 = new Thread(() -> cache.put(3, "bhanu"), "thread3");
        Thread t4 = new Thread(()-> cache.put(4, "nissy"), "thread4");
        Thread t5 = new Thread(()-> cache.get(1), "thread5");
        Thread t6 = new Thread(()-> cache.put(6, "prabhas"), "thread6");
        Thread t7 = new Thread(()-> cache.get(2), "thread7");
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        
        // Wait for all threads to complete
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        
        System.out.println("\n✓ All threads completed successfully!");
    }
}
