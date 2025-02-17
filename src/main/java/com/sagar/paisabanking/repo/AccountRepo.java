package com.sagar.paisabanking.repo;

import com.sagar.paisabanking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Integer> {
    Account findByAccountId(int accountId);

}
