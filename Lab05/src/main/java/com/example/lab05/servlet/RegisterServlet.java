package com.example.lab05.servlet;

import com.example.lab05.dao.ProductDAO;
import com.example.lab05.dao.UserDAO;
import com.example.lab05.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "Register", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;
    HttpSession session;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        session = req.getSession(false);

        if (session != null && session.getAttribute("username") != null) {
            resp.sendRedirect("/product.jsp");
        } else {
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String repassword = req.getParameter("repassword");
            String email = req.getParameter("email");

            if (password.length() < 6 || !password.equals(repassword)) {
                req.setAttribute("passwordError", true);
                req.setAttribute("username", username);
                req.setAttribute("email", email);
                req.getRequestDispatcher("/register.jsp").forward(req,resp);
            } else {
                userDAO = new UserDAO();
                userDAO.add(new User(username, email, password));
                resp.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
