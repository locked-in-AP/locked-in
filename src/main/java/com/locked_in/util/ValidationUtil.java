package com.locked_in.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;
import jakarta.servlet.http.Part;

/**
 * Utility class for input validation operations.
 * 
 * This utility class provides comprehensive validation methods for:
 * - String content validation (empty, alphabetic, alphanumeric)
 * - Personal information validation (gender, email, phone)
 * - Password validation and matching
 * - Image file validation
 * - Age verification
 * 
 * The class implements strict validation rules following:
 * - RFC standards for email validation
 * - Industry best practices for password strength
 * - Common file type restrictions for images
 * - Legal age requirements
 */
public class ValidationUtil {

    /**
     * Validates if a string is null or empty.
     * 
     * Trims the string before checking for emptiness to ensure
     * strings containing only whitespace are considered empty.
     * 
     * @param value the string to validate
     * @return true if the string is null or empty (after trimming)
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates if a string contains only letters, spaces, apostrophes, and hyphens.
     * 
     * The validation allows:
     * - Single spaces between words
     * - Apostrophes and hyphens within words
     * - No consecutive special characters
     * 
     * @param value the string to validate
     * @return true if the string matches the alphabetic pattern
     */
    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$");
    }

    /**
     * Validates if a string starts with a letter and contains only letters and numbers.
     * 
     * This validation ensures:
     * - First character is a letter
     * - Remaining characters are letters or numbers
     * - No special characters or spaces
     * 
     * @param value the string to validate
     * @return true if the string matches the alphanumeric pattern
     */
    public static boolean isAlphanumericStartingWithLetter(String value) {
        return value != null && value.matches("^[A-Za-z][A-Za-z0-9]*$");
    }

    /**
     * Validates if a string represents a valid gender.
     * 
     * Accepts "male" or "female" in any case.
     * 
     * @param value the string to validate
     * @return true if the string is a valid gender value
     */
    public static boolean isValidGender(String value) {
        return value != null && (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female"));
    }

    /**
     * Validates if a string is a valid email address.
     * 
     * Uses RFC-compliant pattern matching to validate:
     * - Local part (before @)
     * - Domain part (after @)
     * - Top-level domain (2-6 characters)
     * 
     * @param email the string to validate
     * @return true if the string is a valid email address
     */
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates if a string is a valid phone number.
     * 
     * Validates phone numbers that:
     * - Are exactly 10 digits long
     * - Start with 97 or 98
     * 
     * @param number the string to validate
     * @return true if the string is a valid phone number
     */
    public static boolean isValidPhoneNumber(String number) {
        return number != null && number.matches("^9[78]\\d{8}$");
    }

    private static final String PASSWORD_REGEX = 
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    /**
     * Validates if a string meets password strength requirements.
     * 
     * Requires passwords to have:
     * - At least 8 characters
     * - At least 1 lowercase letter
     * - At least 1 uppercase letter
     * - At least 1 digit
     * - At least 1 special character (@$!%*?&)
     * 
     * @param password the string to validate
     * @return true if the string meets password requirements
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    /**
     * Validates if a file part has an allowed image extension.
     * 
     * Checks if the file extension is one of:
     * - .jpg
     * - .jpeg
     * - .png
     * - .gif
     * 
     * @param imagePart the file part to validate
     * @return true if the file has an allowed image extension
     */
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

    /**
     * Validates if two password strings match.
     * 
     * Performs a case-sensitive comparison of the passwords.
     * 
     * @param password the first password
     * @param retypePassword the second password to compare
     * @return true if the passwords match
     */
    public static boolean doPasswordsMatch(String password, String retypePassword) {
        return password != null && password.equals(retypePassword);
    }

    /**
     * Validates if a date of birth indicates the person is at least 16 years old.
     * 
     * Calculates the age by comparing the date of birth with the current date.
     * 
     * @param dob the date of birth to validate
     * @return true if the person is at least 16 years old
     */
    public static boolean isAgeAtLeast16(LocalDate dob) {
        if (dob == null) {
            return false;
        }
        return Period.between(dob, LocalDate.now()).getYears() >= 16;
    }
}