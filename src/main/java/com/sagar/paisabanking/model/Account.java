package com.sagar.paisabanking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="account_table")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    private double balance;
    private String type;
    private double accruedInterest;
    private double interestRate;

    public Account() {
        this.type = "savings"; // Setting default type value
        this.balance = 0.0; // Default balance
        this.accruedInterest = 0.0; // Default interest
        this.interestRate = 3.75; // Default interest rate
    }
}
