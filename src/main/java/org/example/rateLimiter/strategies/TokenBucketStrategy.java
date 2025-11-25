package org.example.rateLimiter.strategies;

import org.example.rateLimiter.TokenState;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


public class TokenBucketStrategy implements RateLimiterStrategy {

    int capacity;
    double refillRateSec;
    Map<String, TokenState> clientMap = new HashMap<>();


    public TokenBucketStrategy(int capacity, double refillRateSec) {
        this.capacity = capacity;
        this.refillRateSec = refillRateSec;
    }

    @Override
    public boolean isAllow(String clientId) {
        Instant now = Instant.now();
        TokenState tokenState = clientMap.get(clientId);
        if (tokenState == null) {
            tokenState = new TokenState(capacity, now);
            clientMap.put(clientId, tokenState);
        }
        //refilling
        refill(tokenState.lastRefillInstant, now, clientId);
        //update tokens
        if (tokenState.getTokens() > 0) {
            tokenState.setTokens(tokenState.getTokens() - 1);
            return true;
        }

        return false;

    }

    public void refill(Instant lastRefillMillis, Instant now, String clientId) {
        Duration elapsed = Duration.between(lastRefillMillis, now);
        long elapsedMillis = elapsed.toMillis();
        double intervals = (elapsedMillis) / 100.0;
        int newTokensToAdd = (int) Math.floor(intervals * refillRateSec);

        if (newTokensToAdd > 0) {
            TokenState clientTokenObject = clientMap.get(clientId);
            int tokensToUpdateForClient = Math.min(capacity, clientTokenObject.getTokens() + newTokensToAdd);
            clientTokenObject.tokens = tokensToUpdateForClient;
            clientTokenObject.lastRefillInstant = now;

        }


    }
}
