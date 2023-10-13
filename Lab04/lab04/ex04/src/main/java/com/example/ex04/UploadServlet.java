package com.example.ex04;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String uploadFolder = getServletContext().getRealPath("/uploads");
        Part filePart = request.getPart("document");
        String fileName = filePart.getSubmittedFileName();

        try {
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            List<String> validExtensions = Arrays.asList("txt", "doc", "docx", "img", "pdf", "rar", "zip");
            String newFileName = request.getParameter("fileName") + "." + fileExtension;
            File existingFile = new File(uploadFolder, newFileName);
            if (!validExtensions.contains(fileExtension.toLowerCase())) {
                out.println("Unsupported file extension");
            } else {

                if (existingFile.exists()) {
                    if (request.getParameter("checkbox") != null && request.getParameter("checkbox").equals("on")) {
                        try (InputStream input = filePart.getInputStream()) {
                            Files.copy(input, new File(uploadFolder, fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
                            out.println("File has been overriden");
                            out.println("<p style=\"color:blue\">File has been uploaded. Click <a href='/uploads/" + newFileName + "'>here</a> to visite file </p>");
//            request.setAttribute("uploadMessage", "File has been uploaded");
                        }
                    } else {
                        out.println("File already exists");
                    }

                }else {
                    try (InputStream input = filePart.getInputStream()) {
                        Files.copy(input, existingFile.toPath());
                        out.println("File has been upload");
                        out.println("<p style=\"color:blue\">File has been uploaded. Click <a href='/uploads/" + newFileName + "'>here</a> to visite file </p>");
                    }
                }

            }

        } finally {
            out.close();
        }
//        response.sendRedirect(request.getRequestURI());
    }


}