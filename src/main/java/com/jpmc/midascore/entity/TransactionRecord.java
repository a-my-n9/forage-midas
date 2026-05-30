package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue()
    private long id;

    @ManyToOne
    @JoinColumn(name = "recipientId")
    private UserRecord recipientId;
    @ManyToOne
    @JoinColumn(name = "senderId")
    private UserRecord senderId;
    private float amount;

    protected TransactionRecord() {
    }

    public TransactionRecord(UserRecord senderId, UserRecord recipientId, float amount) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
    }
}
