package com.test.repository;

import com.test.data.db.SubscriberFailOverRecord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class SubscriberFailOverRepository {

    private static final String FETCH_FAIL_OVERS_QUERY ="WITH selected_rows AS (\n" +
            "    SELECT sr.Subscriber_Id, sr.Request_id, blob\n" +
            "    FROM Subscriber_FailOver_records sr, Request_Data rd\n" +
            "    WHERE sr.Request_id=rd.Request_id and  \n" +
            "\tstatus ='pending' and next_processingtime > current_timestamp()\n" +
            "    ORDER BY next_processingtime\n" +
             "LIMIT 10000" + // should be configurable just for completed sake
            "    FOR UPDATE\n" +
            ")\n" +
            "UPDATE Subscriber_FailOver_records\n" +
            "SET status = 'processing'\n" +
            "FROM selected_rows\n" +
            "WHERE Subscriber_FailOver_records.Subscriber_Id = selected_rows.id\n" +
            "RETURNING *;\n";

    // todo batch process the record
    private static final String UPDATE_FAIL_OVER_STATUS= "update Subscriber_FailOver_records set current_retry_attempt=current_retry_attempt + 1, next_processingtime=?, status='pending' where Subscriber_Id=? and Request_id=?";

    private static final String DELETE_FAIL_OVER_RECORD= "delete Subscriber_FailOver_records  where Subscriber_Id=? and Request_id=?";

    public Flux<SubscriberFailOverRecord> fetchFailOvers(){
        // Make call to FETCH_FAIL_OVERS_QUERY, creating some dummy data for processing
        List<SubscriberFailOverRecord> failOverRecords = List.of(new SubscriberFailOverRecord("sub1", "1", "message1".getBytes()),
                new SubscriberFailOverRecord("sub3", "1", "message1".getBytes()),
                new SubscriberFailOverRecord("sub2", "2", "message2".getBytes()),
                new SubscriberFailOverRecord("sub1", "3", "message3".getBytes()),
                new SubscriberFailOverRecord("sub2", "3", "message3".getBytes()),
                new SubscriberFailOverRecord("sub3", "3", "message3".getBytes()));
            return Flux.fromIterable(failOverRecords);
    }


    public Mono<Integer> deleteRecords(String subscriberId, String requestId){
        return Mono.just(1); // Dummy record, mostly delete is void just adding
    }
    public Mono<Integer> updateRecords(String subscriberId, String requestId){
        return Mono.just(1); // Dummy record
    }

}
