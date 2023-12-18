package com.UserManagement.UserManagement.controller;

import com.UserManagement.UserManagement.model.User;
import com.UserManagement.UserManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestParam String userName) {
        return userService.registerUser(userName);
    }

    @PostMapping("/bulk-register")
    public void bulkRegisterUsers(@RequestBody List<String> userNames) {
        userService.bulkRegisterUsers(userNames);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String userName) {
        userService.deleteUser(userName);
    }
    @GetMapping("/password")
    public Optional<User> getUserByPassword(@RequestParam String password) {
        return userService.getUserByPassword(password);
    }
    @GetMapping("/get")
    public User getUserByUserName(@RequestParam String userName) {
        return userService.getUserByUserName(userName);
    }
}
