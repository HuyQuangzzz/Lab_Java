package com.example.ex02;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filename = request.getParameter("file");

        if (filename == null || filename.isEmpty()) {
            response.getWriter().write("File not found");
            return;
        }

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            response.getWriter().write("File not found on the server");
            return;
        }
        String speedParam = request.getParameter("speed");
        int speed = 0;
        if (speedParam != null && !speedParam.isEmpty()) {
            try {
                speed = Integer.parseInt(speedParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.setContentType("application/octet-stream");

        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        long startTime = System.currentTimeMillis();
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);

            if (speed > 0) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                long expectedElapsedTime = (bytesRead * 1000) / (speed * 1024);
                if (elapsedTime < expectedElapsedTime) {
                    try {
                        Thread.sleep(expectedElapsedTime - elapsedTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startTime = System.currentTimeMillis();
            }
        }
        inputStream.close();
        out.close();
    }
}