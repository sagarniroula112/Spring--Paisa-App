package com.sagar.paisabanking.repo;

import com.sagar.paisabanking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
}
