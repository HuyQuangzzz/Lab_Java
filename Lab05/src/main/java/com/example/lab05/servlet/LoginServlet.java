package com.example.lab05.servlet;

import com.example.lab05.dao.UserDAO;
import com.example.lab05.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/login/logout"})
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;
    HttpSession session;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        session = req.getSession(false);
        switch (req.getServletPath()) {
            case "/login":
                if (session != null && session.getAttribute("username") != null) {
                    resp.sendRedirect("/product.jsp");
                } else {
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                }
                break;
            case "/login/logout":
                if (session != null) {
                    session.invalidate();
                }
                resp.sendRedirect("/login.jsp");
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");

            String email = req.getParameter("username");
            String password = req.getParameter("password");
            boolean remember = req.getParameter("remember") != null;

            userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(email);

            if(user == null) {
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
            } else {
                if(user.getPassword().equals(password)){
                    session = req.getSession();
                    session.setAttribute("username", user.getUsername());
                    if (remember) {
                        Cookie emailCookie = new Cookie("email", email);
                        Cookie passwordCookie = new Cookie("password", password);

                        emailCookie.setMaxAge(30 * 24 * 60 * 60);
                        passwordCookie.setMaxAge(30 * 24 * 60 * 60);

                        resp.addCookie(emailCookie);
                        resp.addCookie(passwordCookie);
                    }
                    req.setAttribute("username", user.getUsername());
                    resp.sendRedirect("/product");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
