package com.locked_in.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

/**
 * Utility class for managing cookies in a web application.
 * 
 * This utility class provides methods for:
 * - Adding new cookies with configurable parameters
 * - Retrieving existing cookies by name
 * - Deleting cookies by setting their max age to zero
 * 
 * The class ensures consistent cookie handling across the application
 * by setting appropriate paths and using standard cookie operations.
 */
public class CookieUtil {

    /**
     * Adds a cookie with the specified name, value, and maximum age.
     * 
     * This method:
     * 1. Creates a new cookie with the given name and value
     * 2. Sets the maximum age in seconds
     * 3. Configures the cookie to be available application-wide
     * 4. Adds the cookie to the response
     * 
     * @param response the HttpServletResponse to add the cookie to
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param maxAge the maximum age of the cookie in seconds
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/"); // Make cookie available to the entire application
        response.addCookie(cookie);
    }

    /**
     * Retrieves a cookie by its name from the HttpServletRequest.
     * 
     * This method:
     * 1. Checks if any cookies exist in the request
     * 2. Filters the cookies to find one matching the given name
     * 3. Returns the first matching cookie or null if not found
     * 
     * @param request the HttpServletRequest to get the cookie from
     * @param name the name of the cookie to retrieve
     * @return the Cookie object if found, otherwise null
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Deletes a cookie by setting its max age to 0.
     * 
     * This method:
     * 1. Creates a new cookie with the given name and null value
     * 2. Sets the maximum age to 0 to expire the cookie
     * 3. Configures the cookie to be available application-wide
     * 4. Adds the deletion cookie to the response
     * 
     * @param response the HttpServletResponse to add the deletion cookie to
     * @param name the name of the cookie to delete
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/"); // Make cookie available to the entire application
        response.addCookie(cookie);
    }
}