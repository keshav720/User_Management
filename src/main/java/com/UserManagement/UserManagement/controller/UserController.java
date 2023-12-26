package com.UserManagement.UserManagement.controller;

import com.UserManagement.UserManagement.model.User;
import com.UserManagement.UserManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
    public User registerUser(@RequestParam String userName,String email) {
        return userService.registerUser(userName,email);
    }

    @PostMapping("/bulk-register")
    public void bulkRegisterUsers(@RequestParam("file") MultipartFile file) {
            List<User> userList = readUsersFromCsv(file);
            // Bulk register users
            userService.bulkRegisterUsers(userList);
    }
    private List<User> readUsersFromCsv(MultipartFile file) {
        List<User> userList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                User user = new User();
                user.setUserName(data[0]);
                user.setEmail(data[1]);
                userList.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage());
        }

        return userList;
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
