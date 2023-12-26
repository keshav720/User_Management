package com.UserManagement.UserManagement.services;
import com.UserManagement.UserManagement.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvFileReaderService {
    public List<User> readUsersFromCsv(MultipartFile file) {
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
            // Handle exceptions, log errors, etc.
            e.printStackTrace();
        }

        return userList;
    }
}
