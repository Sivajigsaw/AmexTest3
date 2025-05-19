package com.test.service;

import com.test.common.service.ConfigService;
import com.test.data.config.RateLimitConfig;
import com.test.data.db.SubscriberFailOverRecord;
import com.test.repository.SubscriberFailOverRepository;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;

public class SubscriberOne {
    private static final String SUBSCRIBER_ID = "sub1";
    private static final String SUCCESS = "Success";
    private static final String FAILURE = "Failure";
    private final SubscriberFailOverRepository subscriberFailOverRepository;
    private final RetryBackoffSpec retryBackoffSpec;
    private final RateLimitConfig rateLimitConfig;


    public SubscriberOne(ConfigService configService, SubscriberFailOverRepository subscriberFailOverRepository) {
        this.subscriberFailOverRepository = subscriberFailOverRepository;
        this.retryBackoffSpec = configService.getRetrySpec(SUBSCRIBER_ID);
        this.rateLimitConfig = configService.getRateLimitConfig(SUBSCRIBER_ID);
    }


    public void processData() {
        subscriberFailOverRepository.fetchFailOvers()
                .delayElements(Duration.ofSeconds(rateLimitConfig.getDurationInSeconds()))
                .limitRate(rateLimitConfig.getRateLimit()) // If data needs to chunked and processed in batch we can use buffer here with RateLimit as its input
                .flatMap(this::processData).flatMap(tuple -> {
                    if (SUCCESS.equals(tuple.getT3()))
                        return subscriberFailOverRepository.deleteRecords(tuple.getT1(), tuple.getT2());
                    else {
                        return subscriberFailOverRepository.updateRecords(tuple.getT1(), tuple.getT2());
                    }
                }).retryWhen(retryBackoffSpec).doOnError(error -> System.out.println(error.getMessage())).subscribe();
    }

    private Mono<Tuple3<String, String, String>> processData(SubscriberFailOverRecord subscriberFailOverRecord) {
        return Mono.just(subscriberFailOverRecord).map(data -> {
            System.out.println(new String(data.getBlobData()));
            return Tuples.of(data.getSubscriberId(), data.getRequestId(), SUCCESS);
        }).onErrorReturn(Tuples.of(subscriberFailOverRecord.getSubscriberId(), subscriberFailOverRecord.getRequestId(), FAILURE));
    }


}
