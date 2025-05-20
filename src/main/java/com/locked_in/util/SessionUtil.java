package com.locked_in.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for managing HTTP sessions in a web application.
 * 
 * This utility class provides methods for:
 * - Setting session attributes
 * - Retrieving session attributes
 * - Removing session attributes
 * - Invalidating sessions
 * 
 * The class ensures safe session handling by:
 * - Checking session existence before operations
 * - Handling null sessions gracefully
 * - Providing consistent session management
 */
public class SessionUtil {
    
    /**
     * Sets an attribute in the session.
     * 
     * This method:
     * 1. Gets or creates a session
     * 2. Stores the attribute with the specified key
     * 
     * @param request the HttpServletRequest from which the session is obtained
     * @param key the key under which the attribute is stored
     * @param value the value of the attribute to store in the session
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    /**
     * Retrieves an attribute from the session.
     * 
     * This method:
     * 1. Gets the existing session (does not create a new one)
     * 2. Returns the attribute value if found
     * 3. Returns null if the session doesn't exist or the attribute is not found
     * 
     * @param request the HttpServletRequest from which the session is obtained
     * @param key the key of the attribute to retrieve
     * @return the attribute value, or null if the attribute does not exist or the session is invalid
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    /**
     * Removes an attribute from the session.
     * 
     * This method:
     * 1. Gets the existing session (does not create a new one)
     * 2. Removes the attribute if the session exists
     * 
     * @param request the HttpServletRequest from which the session is obtained
     * @param key the key of the attribute to remove
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * Invalidates the current session.
     * 
     * This method:
     * 1. Gets the existing session (does not create a new one)
     * 2. Invalidates the session if it exists
     * 3. All session attributes are removed
     * 
     * @param request the HttpServletRequest from which the session is obtained
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
