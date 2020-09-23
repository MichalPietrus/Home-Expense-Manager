package com.expense.manager.web;

import com.expense.manager.model.User;
import com.expense.manager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User userRegistrationModel() {
        return new User();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid User user,
                                      BindingResult result) {
        User existingByUserNameOrEmail = userService.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (existingByUserNameOrEmail != null)
            result.rejectValue("username", null, "There is already an account registered with that username or email");

        if (result.hasErrors())
            return "registration";
        userService.registerUser(user);
        return "redirect:/registration?success";
    }

}
