package com.test.data.config;

public class RateLimitConfig {

    public RateLimitConfig(int rateLimit, int durationInSeconds){
        this.rateLimit= rateLimit;
        this.durationInSeconds =durationInSeconds;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public int getRateLimit() {
        return rateLimit;
    }

    private final int durationInSeconds;
    private final int rateLimit;
}
