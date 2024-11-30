/**
 * @project identity-service
 * @author Phong Ha on 30/11/2024
 */

package com.windev.identity_service.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void save(String key, Object value, long ttl, TimeUnit timeUnit) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, jsonValue, ttl, timeUnit);
            log.info("Saved key: {} with TTL: {} seconds", key, ttl);
        } catch (JsonProcessingException e) {
            log.error("Error saving key: {} to Redis. Exception: {}", key, e.getMessage());
            throw new RuntimeException("Failed to save data to Redis");
        }
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String jsonValue = valueOperations.get(key);

            if (jsonValue != null) {
                T value = objectMapper.readValue(jsonValue, clazz);
                return Optional.of(value);
            }
        } catch (Exception e) {
            log.error("Error retrieving key: {} from Redis. Exception: {}", key, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void delete(String key) {
        Boolean deleted = redisTemplate.delete(key);
        if (Boolean.TRUE.equals(deleted)) {
            log.info("Deleted key: {}", key);
        } else {
            log.warn("Failed to delete key: {}", key);
        }
    }

    @Override
    public boolean exists(String key) {
        Boolean exists = redisTemplate.hasKey(key);
        return Boolean.TRUE.equals(exists);
    }
}
