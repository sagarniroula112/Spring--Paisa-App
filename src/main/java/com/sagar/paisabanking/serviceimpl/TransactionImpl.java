package com.sagar.paisabanking.serviceimpl;

import com.sagar.paisabanking.model.Transaction;
import com.sagar.paisabanking.repo.TransactionRepo;
import com.sagar.paisabanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public void addTransaction(Transaction t) {
        transactionRepo.save(t);
    }

    @Override
    public void deleteTransaction(int id) {
        transactionRepo.deleteById(id);
    }

    @Override
    public Transaction getTransactionById(int id) {
        return transactionRepo.findById(id).get();
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }
}
