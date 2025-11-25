package org.example.rateLimiter;

import lombok.Getter;
import lombok.Setter;
import org.example.rateLimiter.strategies.RateLimiterStrategy;

@Getter
@Setter
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
