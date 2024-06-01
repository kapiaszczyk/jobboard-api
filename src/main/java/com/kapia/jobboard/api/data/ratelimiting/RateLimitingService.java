package com.kapia.jobboard.api.data.ratelimiting;

import com.kapia.jobboard.api.auth.controller.AuthController;
import com.kapia.jobboard.api.data.constants.Defaults;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents a rate limiting service that allows controlling the rate at which certain operations can be performed.
 * It uses a token bucket algorithm to limit the number of requests that can be made within a specific time period.
 */
@Service
public class RateLimitingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Value("${rate.limit.bucket.capacity}")
    private int BUCKET_CAPACITY;

    @Value("${rate.limit.refill.period.seconds}")
    private int REFILL_PERIOD_SECONDS;

    @Value("${rate.limit.refill.tokens}")
    private int REFILL_TOKENS;

    private Duration REFILL_PERIOD;

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        LOGGER.info("Global rate limit: {}, refilling with {} tokens per {} seconds", BUCKET_CAPACITY, REFILL_TOKENS, REFILL_PERIOD_SECONDS);

        REFILL_PERIOD = Duration.ofSeconds(REFILL_PERIOD_SECONDS);

        Bandwidth bandwidth = Bandwidth.builder()
                .capacity(BUCKET_CAPACITY).refillGreedy(REFILL_TOKENS, REFILL_PERIOD).build();

        Bucket bucket = Bucket.builder().addLimit(bandwidth).build();

        cache.put(Defaults.GLOBAL_BUCKET_KEY, bucket);
    }

    public boolean tryConsume(String key) {
        Bucket bucket = cache.computeIfAbsent(key, k -> cache.get(Defaults.GLOBAL_BUCKET_KEY));
        return bucket.tryConsume(1);
    }


    public String getRemainingTokens(String defaultKey) {
        Bucket bucket = cache.computeIfAbsent(defaultKey, k -> cache.get(Defaults.GLOBAL_BUCKET_KEY));
        return String.valueOf(bucket.getAvailableTokens());
    }
}
