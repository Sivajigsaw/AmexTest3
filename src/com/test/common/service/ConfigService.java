package com.test.common.service;

import com.test.data.config.RateLimitConfig;
import com.test.repository.ConfigRepository;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigService {

    private final ConfigRepository configRepository;

    private final Map<String, RetryBackoffSpec> retrySpec = new ConcurrentHashMap<>();
    private final Map<String, RateLimitConfig> rateLimitConfig = new ConcurrentHashMap<>();

    public static final String FIXED_SPEC = "fixed";

    public ConfigService(ConfigRepository configRepository){
        this.configRepository = configRepository;
        initializeData();
    }

    private void initializeData() {
        configRepository.getAllSubscriberConfigs().flatMap(eachSpec -> { // could be handled in doOnNext but wanted some parallel processing
            RetryBackoffSpec retryBackoffSpec = null;
            if(FIXED_SPEC.equals(eachSpec.getType())){
                retryBackoffSpec = RetryBackoffSpec.fixedDelay(eachSpec.getMaxRetries(), Duration.ofMinutes((long) eachSpec.getRetryTimeInMinutes()));
            }else{
                retryBackoffSpec = RetryBackoffSpec.backoff(eachSpec.getMaxRetries(), Duration.ofMinutes((long) eachSpec.getRetryTimeInMinutes()));
            }
            rateLimitConfig.put(eachSpec.getSubscriberId(), new RateLimitConfig(eachSpec.getRateLimit(), eachSpec.getRateLimitDurationInSeconds()));
            retrySpec.put(eachSpec.getSubscriberId(), retryBackoffSpec);
            return Mono.just(retryBackoffSpec);
        }).subscribe();
    }


    public RetryBackoffSpec getRetrySpec(String subscriberId){
        return retrySpec.get(subscriberId);
    }

    public RateLimitConfig getRateLimitConfig(String subscriberId){
        return rateLimitConfig.get(subscriberId);
    }
}
