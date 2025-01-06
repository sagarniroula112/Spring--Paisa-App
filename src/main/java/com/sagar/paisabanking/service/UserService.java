package com.sagar.paisabanking.service;

import com.sagar.paisabanking.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    void addUser(User u);
    void deleteUser(int id);
    User getUserById(int id);
    List<User> getAllUsers();
}
