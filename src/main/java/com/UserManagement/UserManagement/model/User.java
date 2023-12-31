package com.UserManagement.UserManagement.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String userName;
    private String email;
    private String password;
    private boolean active;


    public User(String user1, String s) {
        this.userName=user1;
        this.email=s;
    }

    public User(String testUser, String newPassword, String s) {
        this.userName=testUser;
        this.password=newPassword;
        this.email=s;
    }

    public User(String nonExistingUser) {
        this.userName=nonExistingUser;
    }
}
