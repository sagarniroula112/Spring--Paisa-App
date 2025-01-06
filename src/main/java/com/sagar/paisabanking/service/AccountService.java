package com.sagar.paisabanking.service;

import com.sagar.paisabanking.model.Account;

public interface AccountService {
    Account addAccount(Account account);
    void deleteAccount(int id);
    void updateAccount(Account account);
    Account getAccountById(int id);
}
