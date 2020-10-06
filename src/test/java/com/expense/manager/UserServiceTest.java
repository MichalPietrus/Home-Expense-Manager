package com.expense.manager;

import com.expense.manager.model.User;
import com.expense.manager.repository.UserRepository;
import com.expense.manager.service.UserService;
import com.expense.manager.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    /*
    @Mock
    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private UserService userService;

    @BeforeEach
    void initUseCase() {
        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
    }

    @Test
    void savedUserHasAllDataSavedAfterRegistration() {
        User user = new User("Grahul","grahul2121@gmail.com","Jokozuma12");
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        userService.registerUser(user);
        Assertions.assertEquals("Grahul",user.getUsername());
        Assertions.assertEquals("grahul2121@gmail.com",user.getEmail());
        Assertions.assertTrue(bCryptPasswordEncoder.matches("Jokozuma12",user.getPassword()));
        Assertions.assertEquals("ROLE_USER",user.getRoles().stream().findFirst().get().getName());
    }

     */

}
