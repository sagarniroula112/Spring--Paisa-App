package com.sagar.paisabanking.serviceimpl;

import com.sagar.paisabanking.model.Account;
import com.sagar.paisabanking.repo.AccountRepo;
import com.sagar.paisabanking.repo.UserRepo;
import com.sagar.paisabanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public Account addAccount(Account account) {
        accountRepo.save(account);
        return account;
    }

    @Override
    public void deleteAccount(int id) {
        accountRepo.deleteById(id);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepo.save(account);
    }

    @Override
    public Account getAccountById(int id) {
        return accountRepo.findById(id).get();
    }
}
