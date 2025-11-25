package org.example.rateLimiter.strategies;

public interface RateLimiterStrategy {
    boolean isAllow(String clientId);
}
