package org.example.rateLimiter.strategies;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class SlidingWindowStrategy implements RateLimiterStrategy {
    private final int maxRequestsCapacity;
    private final long windowMillis;
    private final Map<String, ArrayDeque<Instant>> timestampsMap = new HashMap<>();

    public SlidingWindowStrategy(int maxRequestsCapacity, long windowMillis) {
        if (maxRequestsCapacity <= 0 || windowMillis <= 0) {
            throw new IllegalArgumentException("maxRequestsCapacity and windowMillis must be > 0");
        }
        this.maxRequestsCapacity = maxRequestsCapacity;
        this.windowMillis = windowMillis;
    }

    @Override
    public boolean isAllow(String clientId) {
        Instant now = Instant.now();
        ArrayDeque<Instant> dq = timestampsMap.get(clientId);
        if (dq == null) {
            dq = new ArrayDeque<>();
            timestampsMap.put(clientId, dq);
        }

        Instant threshold = now.minusMillis(windowMillis);
        while (!dq.isEmpty() && dq.peekFirst().isBefore(threshold)) {
            dq.pollFirst();
        }

        if (dq.size() >= maxRequestsCapacity) {
            return false;
        }

        dq.addLast(now);
        return true;
    }
}
