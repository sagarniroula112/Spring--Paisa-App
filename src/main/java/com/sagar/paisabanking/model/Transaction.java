package com.sagar.paisabanking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="transaction_table")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    private int senderAccNo;
    private int receiverAccNo;
    private double amountExchanged;
    private LocalDateTime dateTime;
    private double updatedBalanceSender;
    private double updatedBalanceReceiver;
}
