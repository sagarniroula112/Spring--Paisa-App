package com.sagar.paisabanking.repo;

import com.sagar.paisabanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllBySenderAccNo(int accountId);
}
