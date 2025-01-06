package com.sagar.paisabanking.serviceimpl;

import com.sagar.paisabanking.model.User;
import com.sagar.paisabanking.repo.UserRepo;
import com.sagar.paisabanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void addUser(User u) {
        userRepo.save(u);
    }

    @Override
    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public User getUserById(int id) {
        return userRepo.findById(id).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
