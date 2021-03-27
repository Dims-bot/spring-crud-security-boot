package ru.jm.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jm.spring.dao.UserDAO;
import ru.jm.spring.dao.UserDAOImplementation;
import ru.jm.spring.model.User;
import ru.jm.spring.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UsersController {

    UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "users/getAllUsers";
    }

    @GetMapping("/users/admin/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));

        return "users/getUserByID";
    }

    @GetMapping("/users/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/newUser";
    }

    @PostMapping("/users/admin")
    public String createNewUser(@ModelAttribute("user") @Valid User user,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "users/newUser";
        userService.save(user);
        return "redirect:/users/admin";
    }

    @GetMapping("/users/admin/{id}/edit")
    public String editUserById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }

    @PatchMapping("/users/admin/{id}")
    public String updateUserById(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "users/edit";

        userService.updateUser(id, user);

        return "users/edit";
    }

    @DeleteMapping("/users/admin/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users/admin";
    }

    @GetMapping("/users/user")
    public String getUserByLogin(Model model, Principal principal) {
        String name = principal.getName();
        model.addAttribute("user", userService.getUserByUsername(name));

        return "/users/user";
    }

    @GetMapping("/login")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "users/login";
    }


}
