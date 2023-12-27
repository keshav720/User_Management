package com.UserManagement.UserManagement.services;

import com.UserManagement.UserManagement.model.User;
import com.UserManagement.UserManagement.repository.UserRepository;
import com.UserManagement.UserManagement.utils.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(new User());

        User registeredUser = userService.registerUser("testUser", "keshavkhandelwal142@gmail.com");

        assertNotNull(registeredUser);
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void registerUser_NonUniqueUsername() {
        when(userRepository.findByUserName(any())).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser("existingUser", "keshavkhandelwal142@gmail.com"));

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(userRepository, never()).save(any());
    }

//    @Test
//    void bulkRegisterUsers_Success() throws ExecutionException, InterruptedException {
//        User user1 = new User("user1", "keshavkhandelwal142@gmail.com");
//        User user2 = new User("user2", "keshavkhandelwal142@gmail.com");
//        List<User> userList = Arrays.asList(user1, user2);
//
//        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());
//        when(userRepository.save(any())).thenReturn(new User());
//
//        // Mock the asynchronous behavior of sendEmail
//        doAnswer(invocation -> {
//            CompletableFuture.runAsync(() -> {
//                emailService.sendEmail("user1", "keshavkhandelwal142@gmail.com", "dummyPassword");
//            });
//            return null;
//        }).when(emailService).sendEmail(eq("user1"), eq("keshavkhandelwal142@gmail.com"), eq("dummyPassword"));
//
//        doAnswer(invocation -> {
//            CompletableFuture.runAsync(() -> {
//                emailService.sendEmail("user2", "keshavkhandelwal142@gmail.com", "dummyPassword");
//            });
//            return null;
//        }).when(emailService).sendEmail(eq("user2"), eq("keshavkhandelwal142@gmail.com"), eq("dummyPassword"));
//
//        // Act
//        userService.bulkRegisterUsers(userList);
//
//        // Allow asynchronous tasks to complete
//        // Allow asynchronous tasks to complete
//        Thread.sleep(2000);
//
//        // Assert
//        verify(emailService, times(1)).sendEmail(eq("user1"), eq("keshavkhandelwal142@gmail.com"), eq("dummyPassword"));
//        verify(emailService, times(1)).sendEmail(eq("user2"), eq("keshavkhandelwal142@gmail.com"), eq("dummyPassword"));
//        verify(userRepository, times(2)).save(any());
//    }
//
//    @Test
//    void updateUser_Success() {
//        when(userRepository.findByUserName(any())).thenReturn(Optional.of(new User()));
//
//        // Create an updatedUser with a non-null password
//        User updatedUser = new User("keshav", "newPassword", "keshavkhandelwal142@gmail.com");
//
//        // Mock PasswordUtils.hashPassword method
//        String encryptedPassword = "encryptedPassword";
//        System.out.println(updatedUser.getPassword());
//        when(PasswordUtils.hashPassword(updatedUser.getPassword())).thenReturn(encryptedPassword);
//
//        // Mock userRepository.save to return a user
//        when(userRepository.save(any())).thenReturn(new User());
//
//        // Call the updateUser method
//        userService.updateUser(updatedUser);
//
//        // Verify that findByUserName and save methods are called
//        verify(userRepository, times(1)).findByUserName(any());
//        verify(userRepository, times(1)).save(any());
//    }


    @Test
    void updateUser_UserNotFound() {
        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(new User("nonExistingUser")));

        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.findByUserName(any())).thenReturn(Optional.of(new User()));
        when(userRepository.save(any())).thenReturn(new User());

        userService.deleteUser("testUser");

        verify(userRepository, times(1)).findByUserName(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void deleteUser_UserNotFound() {
        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser("nonExistingUser"));

        verify(userRepository, never()).save(any());
    }

    @Test
    void getUserByUserName_Success() {
        when(userRepository.findByUserName(any())).thenReturn(Optional.of(new User()));

        User user = userService.getUserByUserName("testUser");

        assertNotNull(user);
        verify(userRepository, times(1)).findByUserName(any());
    }

    @Test
    void getUserByUserName_UserNotFound() {
        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());

        User user = userService.getUserByUserName("nonExistingUser");

        assertNull(user);
        verify(userRepository, times(1)).findByUserName(any());
    }

    @Test
    void isUniqueUserName_Unique() {
        when(userRepository.findByUserName(any())).thenReturn(Optional.empty());

        assertTrue(userService.isUniqueUserName("newUser"));
        verify(userRepository, times(1)).findByUserName(any());
    }

    @Test
    void isUniqueUserName_NonUnique() {
        when(userRepository.findByUserName(any())).thenReturn(Optional.of(new User()));

        assertFalse(userService.isUniqueUserName("existingUser"));
        verify(userRepository, times(1)).findByUserName(any());
    }

    @Test
    void getUserByPassword_Success() {
        when(userRepository.findByPassword(any())).thenReturn(Optional.of(new User()));

        Optional<User> user = userService.getUserByPassword("password");

        assertTrue(user.isPresent());
        verify(userRepository, times(1)).findByPassword(any());
    }

    @Test
    void getUserByPassword_UserNotFound() {
        when(userRepository.findByPassword(any())).thenReturn(Optional.empty());

        Optional<User> user = userService.getUserByPassword("nonExistingPassword");

        assertFalse(user.isPresent());
        verify(userRepository, times(1)).findByPassword(any());
    }

}
