package com.sagar.paisabanking.repo;

import com.sagar.paisabanking.model.Utilpayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilpaymentRepo extends JpaRepository<Utilpayment, Integer> {
    List<Utilpayment> findAllBySenderAccNo(int senderAccNo);
}
