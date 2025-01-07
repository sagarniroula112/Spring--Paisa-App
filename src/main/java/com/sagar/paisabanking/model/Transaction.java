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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getSenderAccNo() {
        return senderAccNo;
    }

    public void setSenderAccNo(int senderAccNo) {
        this.senderAccNo = senderAccNo;
    }

    public int getReceiverAccNo() {
        return receiverAccNo;
    }

    public void setReceiverAccNo(int receiverAccNo) {
        this.receiverAccNo = receiverAccNo;
    }

    public double getAmountExchanged() {
        return amountExchanged;
    }

    public void setAmountExchanged(double amountExchanged) {
        this.amountExchanged = amountExchanged;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getUpdatedBalanceSender() {
        return updatedBalanceSender;
    }

    public void setUpdatedBalanceSender(double updatedBalanceSender) {
        this.updatedBalanceSender = updatedBalanceSender;
    }

    public double getUpdatedBalanceReceiver() {
        return updatedBalanceReceiver;
    }

    public void setUpdatedBalanceReceiver(double updatedBalanceReceiver) {
        this.updatedBalanceReceiver = updatedBalanceReceiver;
    }
}
