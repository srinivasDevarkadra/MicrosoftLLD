# In-Memory Cache - Multi-Threaded Implementation

## Why ReentrantReadWriteLock Instead of Synchronized?

### Overview
This cache uses `ReentrantReadWriteLock` instead of traditional `synchronized` blocks for better performance and concurrency.

### Key Differences

#### 1. **Multiple Concurrent Readers**
- **ReentrantReadWriteLock**: Multiple threads can acquire the **read lock** simultaneously
- **Synchronized**: Only ONE thread can execute at a time (mutual exclusion)

```
ReentrantReadWriteLock:
Thread1 (read)  ✓ Allowed
Thread2 (read)  ✓ Allowed  <- Can run in parallel
Thread3 (read)  ✓ Allowed

Synchronized:
Thread1 (read)  ✓ Executing
Thread2 (read)  ⏳ Waiting
Thread3 (read)  ⏳ Waiting
```

#### 2. **Write Lock Exclusivity**
- **ReentrantReadWriteLock**: Write lock blocks ALL readers and other writers
- **Synchronized**: Same behavior, but less efficient for read-heavy workloads

#### 3. **Performance Benefits**
- **Read-Heavy Workloads**: ReentrantReadWriteLock is **much faster** because multiple `get()` calls don't block each other
- **Write-Heavy Workloads**: Similar performance to synchronized
- **Mixed Workloads**: ReentrantReadWriteLock wins due to parallel reads

### Use Case in This Cache

```java
// Multiple threads can read simultaneously
Thread1: cache.get(1)  ✓ Read lock acquired
Thread2: cache.get(2)  ✓ Read lock acquired (parallel)
Thread3: cache.get(3)  ✓ Read lock acquired (parallel)

// Write blocks everything
Thread4: cache.put(4, "value")  ✓ Write lock acquired
Thread1: cache.get(1)  ⏳ Blocked (waiting for write lock)
Thread2: cache.get(2)  ⏳ Blocked (waiting for write lock)
```

### When to Use Each

| Scenario | ReentrantReadWriteLock | Synchronized |
|----------|------------------------|--------------|
| Read-heavy cache | ✅ Best choice | ❌ Slower |
| Write-heavy cache | ✅ Good | ✅ Acceptable |
| Simple code | ❌ More complex | ✅ Simpler |
| High concurrency | ✅ Excellent | ❌ Bottleneck |

### Implementation Details

- **Read Lock** (`get()` method): Allows multiple concurrent readers
- **Write Lock** (`put()` method): Exclusive access for modifications
- **Eviction Policy**: Protected by write lock, no additional synchronization needed

### Conclusion

For a cache with frequent reads and occasional writes, `ReentrantReadWriteLock` provides **superior performance** compared to `synchronized` by allowing parallel read operations.
