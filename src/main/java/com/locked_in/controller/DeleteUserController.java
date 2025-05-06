package com.locked_in.controller;

import com.locked_in.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = { "/deleteUser" })
public class DeleteUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/deleteUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIdStr = request.getParameter("userId");
        try {
            int userId = Integer.parseInt(userIdStr);
            boolean deleted = userService.deleteUser(userId);
            if (deleted) {
                request.setAttribute("success", "User deleted successfully!");
            } else {
                request.setAttribute("error", "Failed to delete user. Please check the ID and try again.");
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/pages/deleteUser.jsp").forward(request, response);
    }
} 