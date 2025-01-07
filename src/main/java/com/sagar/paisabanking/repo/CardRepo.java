package com.sagar.paisabanking.repo;

import com.sagar.paisabanking.model.Account;
import com.sagar.paisabanking.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepo extends JpaRepository<Card, Long> {
    List<Card> findAllByAccount(Account account);
}
