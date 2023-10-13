package com.example.ex06;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        String birthtime = request.getParameter("birthtime");
        String gender = request.getParameter("gender");
        String country = request.getParameter("country");
        String[] favoriteIdeas = request.getParameterValues("favorite_ide[]");
        String toeic = request.getParameter("toeic");
        String message = request.getParameter("message");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Registration Details</title></head>");
        out.println("<body>");
        out.println("<table class=\"my-table\" style=\"width: 100%; border-collapse: collapse;\">");
        out.println("<tr>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">Họ và tên</td>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">" + name + "</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">Email</td>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">" + email + "</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">Ngày sinh</td>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">" + birthday + "</td>");
        out.println("</tr>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">Gio sinh</td>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">" + birthtime + "</td>");
        out.println("</tr>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">Gioi tinh</td>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">" + gender + "</td>");
        out.println("</tr>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">Quoc gia</td>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">" + country + "</td>");
        out.println("</tr>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">IDE yêu thích</td>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">");
        if (favoriteIdeas != null) {
            for (String ide : favoriteIdeas) {
                out.println(ide + " " +"<br>");
            }
        }
        out.println("</td>");
        out.println("</tr>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">Điểm TOEIC</td>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">" + toeic + "</td>");
        out.println("</tr>");
        out.println("</tr>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">Gioi thieu bản than</td>");
        out.println("<td style=\"border: 1px solid black; padding: 10px; text-align: center;\">" + message + "</td>");
        out.println("</tr>");

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}

