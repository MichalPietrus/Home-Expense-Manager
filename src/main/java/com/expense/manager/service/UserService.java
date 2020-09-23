package com.expense.manager.service;

import com.expense.manager.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User findByEmail(String email);

    void registerUser(User user);

    void save(User user);

    User findByUsernameOrEmail(String username, String email);

}
