package com.sagar.paisabanking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="utilpayment_table")
public class Utilpayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    private int senderAccNo;
    private String receiver;
    private double amountExchanged;
    private LocalDateTime dateTime;
    private double updatedBalanceSender;
    private String transactionType;
    private String remarks;
    private String info;
    private String status;
//    private double updatedBalanceReceiver;

    @Override
    public String toString() {
        return "Utilpayment{" +
                "id=" + id +
                ", account=" + account +
                ", senderAccNo=" + senderAccNo +
                ", receiver=" + receiver +
                ", amountExchanged=" + amountExchanged +
                ", dateTime=" + dateTime +
                ", updatedBalanceSender=" + updatedBalanceSender +
                ", transactionType='" + transactionType + '\'' +
                ", remarks='" + remarks + '\'' +
                ", info='" + info + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
