package ru.jm.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
//@RequestMapping("/users") // для всех контроллеров пред их персоналтным "адресом"
public class UsersController {

    //@Autowired
    UserService userService;

    public UsersController(UserService userService) {this.userService = userService;}

    @GetMapping("/users/admin")
    public String getAllUsers(Model model) {     // get all users from DAO & pass to showing by thymeleaf
        model.addAttribute("users", userService.getAllUsers());

        return "users/getAllUsers";
    }

    @GetMapping("/users/admin/{id}")                       // insert some number and the number will be use as argument the method
    public String getUserById(@PathVariable("id") int id, Model model) { // through @PathVariable in int id will be number from url
        model.addAttribute("user", userService.getUserById(id));

        // get one user by id from DAO & pass to showing by thymeleaf
        return "users/getUserByID";
    }

    @GetMapping("/users/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/newUser";
    }

    @PostMapping("users/admin")
    public String createNewUser(@ModelAttribute("user") @Valid User user,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "users/newUser";
        userService.save(user);
        return "redirect:/users/getAllUsers";
    }

    @GetMapping("/users/admin/{id}/edit")
    public String editUserById(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }

    @PatchMapping("users/admin/{id}")
    public String updateUserById(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors())
            return "users/edit";
        userService.updateUser(id, user);
        //userService.save(user);

        return "redirect:/users/edit";
    }

    @DeleteMapping("users/admin/{id}")
    public String deleteUserById(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/users/getAllUsers";
    }

    //@ResponseBody
    @GetMapping("users/user")
    public String getUserByLogin (Principal principal, Model model) {
        String name= principal.getName();
        model.addAttribute("user", userService.getUserByUsername(name));
        return "users/user";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "users/login";
    }

    // Login form with error
//    @RequestMapping("/login-error")
//    public String loginError(Model model) {
//        model.addAttribute("loginError", true);
//        return "users/login";
//    }




}
