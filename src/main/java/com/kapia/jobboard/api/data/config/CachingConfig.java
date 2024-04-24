package com.kapia.jobboard.api.data.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CachingConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachingConfig.class);

    @Value("${cache.initial.capacity}")
    private int INITIAL_CAPACITY;

    @Value("${cache.maximum.size}")
    private int MAXIMUM_SIZE;

    @Value("${cache.expire.after.write.minutes}")
    private int EXPIRE_AFTER_WRITE_MINUTES;

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

    @Bean
    public Caffeine caffeineConfig() {

        LOGGER.info(
                "Configuring Caffeine cache with initial capacity: {}, maximum size: {}, and expiry after write: {} minutes",
                INITIAL_CAPACITY, MAXIMUM_SIZE, EXPIRE_AFTER_WRITE_MINUTES);

        return Caffeine.newBuilder()
                .initialCapacity(INITIAL_CAPACITY)
                .maximumSize(MAXIMUM_SIZE)
                .expireAfterWrite(Duration.ofMinutes(EXPIRE_AFTER_WRITE_MINUTES))
                .recordStats();
    }

}
