package org.example.rateLimiter;

import org.example.rateLimiter.strategies.SlidingWindowStrategy;
import org.example.rateLimiter.strategies.TokenBucketStrategy;

public class RateLimiterClient {
    public static void main(String[] args) throws InterruptedException {

        TokenBucketStrategy tokenStrategy = new TokenBucketStrategy(5, 2);
        RateLimiterContext limiter = new RateLimiterContext(tokenStrategy);

        System.out.println("=== TokenBucket Strategy Test (capacity=100, refill=100/sec) ===");
        testBurst(limiter, "clientA", 105);

        // Extra: Sliding Window Strategy test
        SlidingWindowStrategy slidingStrategy = new SlidingWindowStrategy(5, 1000L);
        limiter.setStrategy(slidingStrategy);
        System.out.println("=== SlidingWindow Strategy Test (maxRequests=5, window=1000ms) ===");
        testBurst(limiter, "clientA", 12);
    }

    private static void testBurst(RateLimiterContext limiter, String clientId, int requests) throws InterruptedException {
        int allowed = 0, denied = 0;
        for (int i = 0; i < requests; i++) {
            if (limiter.isAllow(clientId)) allowed++;
            else denied++;
            // small sleep optional for readability or to observe refill behavior
            // Thread.sleep(1);
        }
        System.out.printf("%s -> allowed=%d denied=%d%n", clientId, allowed, denied);
    }
}