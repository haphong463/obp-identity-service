package com.windev.identity_service.service.cache;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    /**
     * Lưu dữ liệu vào Redis.
     *
     * @param key   Key của dữ liệu.
     * @param value Giá trị cần lưu trữ.
     * @param ttl   Thời gian sống của dữ liệu (tính bằng giây).
     */
    void save(String key, Object value, long ttl, TimeUnit timeUnit);

    /**
     * Lấy dữ liệu từ Redis.
     *
     * @param key Key của dữ liệu cần lấy.
     * @param <T> Kiểu dữ liệu mong muốn.
     * @return Optional chứa dữ liệu nếu tồn tại, ngược lại là Optional.empty().
     */
    <T> Optional<T> get(String key, Class<T> clazz);

    /**
     * Xoá dữ liệu khỏi Redis.
     *
     * @param key Key của dữ liệu cần xoá.
     */
    void delete(String key);

    /**
     * Kiểm tra sự tồn tại của một key trong Redis.
     *
     * @param key Key cần kiểm tra.
     * @return true nếu key tồn tại, ngược lại false.
     */
    boolean exists(String key);
}
