package com.UserManagement.UserManagement.services;

import com.UserManagement.UserManagement.model.User;
import com.UserManagement.UserManagement.repository.UserRepository;
import com.UserManagement.UserManagement.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private static final AtomicBoolean uniqueUserFlag = new AtomicBoolean(true);


    @Autowired
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }


    public User registerUser(String userName,String email) {
        if (!isUniqueUserName(userName)) {
            throw new IllegalArgumentException("Username is not unique: " + userName);
        }

        String password = PasswordUtils.generatePassword(userName);
         String encryptedPassword = PasswordUtils.hashPassword(password);

        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        user.setActive(true);
        emailService.sendEmail(userName,email,password);


        userRepository.save(user);


        // Log registration details
        System.out.println("User Registered: " + user);

        return user;
    }

    public void bulkRegisterUsers(List<User> users) {
        ExecutorService executor = Executors.newFixedThreadPool(users.size());

        for (User user : users) {
            executor.submit(() -> registerUser(user.getUserName(), user.getEmail()));

        }

        executor.shutdown();
    }

    public void updateUser(User updatedUser) {
        User existingUser = getUserByUserName(updatedUser.getUserName());

        if (existingUser != null) {
            existingUser.setPassword(PasswordUtils.hashPassword(updatedUser.getPassword()));
            existingUser.setEmail(updatedUser.getEmail());
            userRepository.save(existingUser);

            // Log update details
            System.out.println("User Updated: " + existingUser);
        } else {
            throw new IllegalArgumentException("User not found for update: " + updatedUser.getUserName());
        }
    }


    public void deleteUser(String userName) {
        User existingUser = getUserByUserName(userName);

        if (existingUser != null) {
            existingUser.setActive(false);
            userRepository.save(existingUser);
            // Log deletion details
            System.out.println("User Deleted: " + existingUser);
        } else {
            throw new IllegalArgumentException("User not found for deletion: " + userName);
        }
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName).orElse(null);
    }

    boolean isUniqueUserName(String userName) {
        return userRepository.findByUserName(userName).isEmpty();
    }

    public Optional<User> getUserByPassword(String password) {
        String hashedEnteredPassword = PasswordUtils.hashPassword(password);
        return  userRepository.findByPassword(hashedEnteredPassword);
    }
}
