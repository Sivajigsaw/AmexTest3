package com.test.data.config;

public class SubscriberConfig {

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    private String subscriberId;

    public int getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(int rateLimit) {
        this.rateLimit = rateLimit;
    }

    public int getRetryTimeInMinutes() {
        return retryTimeInMinutes;
    }

    public void setRetryTimeInMinutes(int retryTimeInMinutes) {
        this.retryTimeInMinutes = retryTimeInMinutes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    private int rateLimit;
    private int retryTimeInMinutes;
    private String type;
    private int maxRetries;

    public int getRateLimitDurationInSeconds() {
        return rateLimitDurationInSeconds;
    }

    public void setRateLimitDurationInSeconds(int rateLimitDurationInSeconds) {
        this.rateLimitDurationInSeconds = rateLimitDurationInSeconds;
    }

    private int rateLimitDurationInSeconds;

}
