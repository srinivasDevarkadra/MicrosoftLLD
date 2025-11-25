package org.example.rateLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class TokenState {
    public int tokens;
    public Instant lastRefillInstant;

}
