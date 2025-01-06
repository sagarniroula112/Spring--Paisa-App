package com.sagar.paisabanking.service;

import com.sagar.paisabanking.model.Transaction;

import java.util.List;

public interface TransactionService {
    void addTransaction(Transaction t);
    void deleteTransaction(int id);
    Transaction getTransactionById(int id);
    List<Transaction> getAllTransactions();
}
