package com.sagar.paisabanking.repo;

import com.sagar.paisabanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
}
