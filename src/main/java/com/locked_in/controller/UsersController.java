package com.locked_in.controller;

import com.locked_in.service.UserService;
import com.locked_in.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = { "/users" })
public class UsersController extends HttpServlet {
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
        List<UserModel> users = userService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }
} 