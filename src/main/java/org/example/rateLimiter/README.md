# Rate Limiter Implementation

A Java-based rate limiting library that supports multiple rate limiting strategies. This implementation is designed to
be flexible, thread-safe, and easy to integrate into any Java application.

## Features

- **Multiple Strategies**: Implements different rate limiting algorithms
    - Token Bucket
    - Sliding Window
- **Per-client rate limiting**: Track and limit requests per client
- **Thread-safe**: Safe for concurrent use
- **Configurable**: Customize rate limits and window sizes

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven (for building from source)

### Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd MicrosoftLLD
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

## Usage

### Basic Usage

```java
import org.example.rateLimiter.RateLimiterContext;
import org.example.rateLimiter.strategies.TokenBucketStrategy;
import org.example.rateLimiter.strategies.SlidingWindowStrategy;

// Create a rate limiter with TokenBucket strategy (5 tokens, refill 2 tokens per second)
TokenBucketStrategy tokenStrategy = new TokenBucketStrategy(5, 2);
RateLimiterContext limiter = new RateLimiterContext(tokenStrategy);

// Check if request is allowed
if(limiter.

isAllow("client1")){
        // Process the request
        }else{
        // Handle rate limited request
        }

// Switch to SlidingWindow strategy (max 5 requests per second)
SlidingWindowStrategy slidingStrategy = new SlidingWindowStrategy(5, 1000L);
limiter.

setStrategy(slidingStrategy);
```

### Available Strategies

#### 1. Token Bucket

- **Constructor**: `TokenBucketStrategy(int capacity, int refillRatePerSecond)`
- **How it works**:
    - Each client gets a bucket with a fixed number of tokens
    - Each request consumes one token
    - Tokens are refilled at a fixed rate
    - If no tokens are available, the request is rate limited

#### 2. Sliding Window

- **Constructor**: `SlidingWindowStrategy(int maxRequests, long windowSizeInMillis)`
- **How it works**:
    - Tracks requests in a sliding time window
    - Allows up to `maxRequests` in any `windowSizeInMillis` period
    - More accurate rate limiting than fixed window approaches

## Example

```java
// Example from RateLimiterClient.java
TokenBucketStrategy tokenStrategy = new TokenBucketStrategy(5, 2);
RateLimiterContext limiter = new RateLimiterContext(tokenStrategy);

// Test with a burst of requests
testBurst(limiter, "clientA",12);

// Switch to SlidingWindow strategy
SlidingWindowStrategy slidingStrategy = new SlidingWindowStrategy(5, 1000L);
limiter.

setStrategy(slidingStrategy);

testBurst(limiter, "clientB",12);
```

## Thread Safety

All provided implementations are thread-safe and can be used in concurrent environments.

## Performance Considerations

- **Token Bucket**: O(1) time complexity for each operation
- **Sliding Window**: O(1) average time complexity, with O(n) in worst case for cleanup
