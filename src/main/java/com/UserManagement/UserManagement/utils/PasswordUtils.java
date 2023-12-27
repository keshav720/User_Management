package com.UserManagement.UserManagement.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtils {
    private static final int PASSWORD_LENGTH = 12; // You can adjust the length as needed
    private static final String PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String generatePassword(String userName) {
        StringBuilder password = new StringBuilder(userName);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(PASSWORD_CHARACTERS.length());
            password.append(PASSWORD_CHARACTERS.charAt(randomIndex));
        }
        int maskLength = Math.min(password.length() / 2, 3);
        return password.substring(0, maskLength) +
                "*"+password.substring(maskLength,password.length());
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            // Convert the byte array to a hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
