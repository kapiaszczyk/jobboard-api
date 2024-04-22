package com.kapia.jobboard.api.data.ratelimiting;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    private static final int BUCKET_CAPACITY = 100;
    private static final int REFILL_TOKENS = 100;
    private static final Duration REFILL_PERIOD = Duration.ofSeconds(60);
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public RateLimitingService() {

        Bandwidth bandwidth = Bandwidth.builder()
                .capacity(BUCKET_CAPACITY).refillGreedy(REFILL_TOKENS, REFILL_PERIOD).build();

        Bucket bucket = Bucket.builder().addLimit(bandwidth).build();

        cache.put("global_bucket", bucket);

    }


    public boolean tryConsume(String key) {
        Bucket bucket = cache.computeIfAbsent(key, k -> cache.get("global_bucket"));
        return bucket.tryConsume(1);
    }


}
