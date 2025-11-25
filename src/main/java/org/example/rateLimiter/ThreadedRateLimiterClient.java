package org.example.rateLimiter;

import org.example.rateLimiter.strategies.SlidingWindowStrategy;
import org.example.rateLimiter.strategies.TokenBucketStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadedRateLimiterClient {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== TokenBucket test (capacity=5, refill=1 tok/sec) ===");
        TokenBucketStrategy tokenBucket = new TokenBucketStrategy(5, 1.0);
        RateLimiterContext ctx = new RateLimiterContext(tokenBucket);

        runTwoThreadTest(ctx, "client-1");

        System.out.println();
        System.out.println("=== SlidingWindow test (max=3, window=2000ms) ===");
        SlidingWindowStrategy sliding = new SlidingWindowStrategy(3, 2000);
        ctx.setStrategy(sliding);

        runTwoThreadTest(ctx, "client-2");
    }

    public static void runTwoThreadTest(RateLimiterContext context, String clientId) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            try {
                for (int i = 0; i < 20; i++) {
                    boolean allowed = context.getStrategy().isAllow(clientId);
                    System.out.println(Thread.currentThread().getName()
                            + " | client=" + clientId + " | allowed=" + allowed + " | i=" + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
                throw t;
            }
        };

        /**
         * If you submitted only once,
         * only one worker would run the workload and the other pool thread would be idle
         * — so you would not test concurrency.
         * Submitting twice runs the same Runnable in two different threads at the same time.
         */

        executorService.submit(task);
        executorService.submit(task);

        // shutdown and wait safely
        executorService.shutdown();
        try {
            // wait indefinitely until tasks finish (or until current thread interrupted)
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ie) {
            // if interrupted while waiting, stop tasks and restore interrupt flag
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Two-thread test for client '" + clientId + "' finished.");
    }

}
