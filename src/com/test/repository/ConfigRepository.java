package com.test.repository;

import com.test.data.config.SubscriberConfig;
import reactor.core.publisher.Flux;
import java.util.List;

import static com.test.common.service.ConfigService.FIXED_SPEC;

public class ConfigRepository {

    // Assume a DB call and all Subscriber data is fetched
    public Flux<SubscriberConfig> getAllSubscriberConfigs(){
        SubscriberConfig subscriberConfig = new SubscriberConfig();
        subscriberConfig.setSubscriberId("sub1");
        subscriberConfig.setType(FIXED_SPEC);
        subscriberConfig.setMaxRetries(10);
        subscriberConfig.setRateLimit(1000);
        subscriberConfig.setRateLimitDurationInSeconds(60);
        subscriberConfig.setRetryTimeInMinutes(30);
        List<SubscriberConfig> configs = List.of(subscriberConfig); // config Objects go here
        return Flux.fromIterable(configs);
    }



}
