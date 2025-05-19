package com.test.repository;

import com.test.data.config.SubscriberConfig;
import reactor.core.publisher.Flux;
import java.util.ArrayList;
import java.util.List;

public class ConfigRepository {

// Assume a DB call and all Subscriber data is fetched
    public Flux<SubscriberConfig> getAllSubscriberConfigs(){
        List<SubscriberConfig> configs = new ArrayList<>(); // config Objects go here
        return Flux.fromIterable(configs);
    }



}
