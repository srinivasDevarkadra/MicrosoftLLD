package org.example.rateLimiter;

import org.example.rateLimiter.strategies.RateLimiterStrategy;

class RateLimiterContext {
    private RateLimiterStrategy strategy;

    public RateLimiterContext(RateLimiterStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean isAllow(String clientId) {
        return strategy.isAllow(clientId);
    }

    public void setStrategy(RateLimiterStrategy strategy) {
        this.strategy = strategy;
    }
}
