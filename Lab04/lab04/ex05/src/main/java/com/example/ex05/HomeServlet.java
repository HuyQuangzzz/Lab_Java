package com.example.ex05;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = request.getParameter("page");
        if (page != null) {
            switch (page) {
                case "about":
                    request.getRequestDispatcher("/about.jsp").forward(request, response);
                    break;
                case "contact":
                    request.getRequestDispatcher("/contact.jsp").forward(request, response);
                    break;
                case "help":
                    request.getRequestDispatcher("/help.jsp").forward(request, response);
                    break;
                default:
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<html><body>");
                    out.println("<h1>Welcome to our website</h1>");
                    out.println("</body></html>");
            }
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>Welcome to our website</h1>");
            out.println("</body></html>");
        }
    }
   
}
