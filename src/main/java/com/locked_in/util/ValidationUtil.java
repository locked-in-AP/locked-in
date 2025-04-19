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

    // 2. Validate if a string contains only letters, single spaces, apostrophes, and hyphens
    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$");
    }


    // 3. Validate if a string starts with a letter and is composed of letters and numbers
    public static boolean isAlphanumericStartingWithLetter(String value) {
        return value != null && value.matches("^[A-Za-z][A-Za-z0-9]*$");
    }

    // 4. Validate if a string is "male" or "female" (case insensitive)
    public static boolean isValidGender(String value) {
        return value != null && (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female"));
    }

    // 5. Validate if a string is a valid email address (RFC‑style)
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    // 6. Validate if a number is exactly 10 digits, starting with 97 or 98
    public static boolean isValidPhoneNumber(String number) {
        return number != null && number.matches("^9[78]\\d{8}$");
    }

    // 7. Validate if a password has at least 8 chars, 1 lowercase, 1 uppercase, 1 digit, 1 special
    private static final String PASSWORD_REGEX = 
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static boolean isValidPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    // 8. Validate if a Part's file extension matches image types
    public static boolean isValidImageExtension(Part imagePart) {
        if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
            return false;
        }
        String fileName = imagePart.getSubmittedFileName().toLowerCase();
        return fileName.endsWith(".jpg")
            || fileName.endsWith(".jpeg")
            || fileName.endsWith(".png")
            || fileName.endsWith(".gif");
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
        return Period.between(dob, LocalDate.now()).getYears() >= 16;
    }
}