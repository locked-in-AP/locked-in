package com.locked_in.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;
import jakarta.servlet.http.Part;

public class ValidationUtil {

    // 1. Validate if a field is null or empty
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // 2. Validate if a string contains only letters
    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("^[a-zA-Z]+$");
    }

    // 3. Validate if a string starts with a letter and is composed of letters and numbers
    public static boolean isAlphanumericStartingWithLetter(String value) {
        return value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
    }

    // 4. Validate if a string is "male" or "female" (case insensitive)
    public static boolean isValidGender(String value) {
        return value != null && (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female"));
    }

    // 5. Validate if a string is a valid email address
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[a-zA-Z]{2,}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    // 6. Validate if a number is a valid phone number (more flexible)
    public static boolean isValidPhoneNumber(String number) {
        // Allow phone numbers with or without country code, with flexibility in format
        return number != null && number.matches("^(\\+?\\d{1,3}[-.\\s]?)?\\d{8,10}$");
    }

    // 7. Validate if a password meets security requirements
    public static boolean isValidPassword(String password) {
        // At least 8 chars, 1 uppercase, 1 digit, and 1 special character
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(passwordRegex);
    }

    // 8. Validate if a Part's file extension matches with image extensions
    public static boolean isValidImageExtension(Part imagePart) {
        if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
            return false;
        }
        String fileName = imagePart.getSubmittedFileName().toLowerCase();
        // Checking file extensions
        boolean isValidExtension = fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || 
                                  fileName.endsWith(".png") || fileName.endsWith(".gif");

        // Optionally, check MIME type to ensure it's an image
        String mimeType = imagePart.getContentType();
        boolean isValidMimeType = mimeType != null && mimeType.startsWith("image/");

        return isValidExtension && isValidMimeType;
    }

    // 9. Validate if password and retype password match
    public static boolean doPasswordsMatch(String password, String retypePassword) {
        return password != null && password.equals(retypePassword);
    }

    // 10. Validate if the date of birth is at least 16 years before today
    public static boolean isAgeAtLeast16(LocalDate dob) {
        if (dob == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        int age = Period.between(dob, today).getYears();

        // Handle age case correctly
        if (age < 16) {
            return false;
        } else if (age == 16) {
            // For exact age of 16, ensure their birthday has passed this year
            return !dob.plusYears(16).isAfter(today);
        }
        return true;
    }
}