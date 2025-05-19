package com.test.data.db;

import java.time.LocalDateTime;

public class SubscriberFailOverRecord {

    private String subscriberId;
    private String requestId;
    public SubscriberFailOverRecord(String subscriberId, String requestId, byte[] data){
    this.subscriberId=subscriberId;
    this.requestId=requestId;
    this.blobData = data;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public byte[] getBlobData() {
        return blobData;
    }

    public void setBlobData(byte[] blobData) {
        this.blobData = blobData;
    }

    private byte[] blobData;




}
