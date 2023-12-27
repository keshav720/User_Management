package com.UserManagement.UserManagement.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRepositoryTest {
    @Id
    private String id;
    private String userName;
    private String email;
    private String password;
    private boolean active;

}
