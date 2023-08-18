package com.tenpo.challenge.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateLimitControllerTest {

    @Test
    void rateLimitTest(){
        Refill refill = Refill.intervally(3, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(3, refill);
        Bucket bucket = Bucket.builder()
                .addLimit(limit)
                .build();

        for (int i = 1; i <= 3; i++) {
            assertTrue(bucket.tryConsume(1));
        }
        assertFalse(bucket.tryConsume(1));
    }


}
