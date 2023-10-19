package com.example.lab05.servlet;

import com.example.lab05.dao.ProductDAO;
import com.example.lab05.dao.UserDAO;
import com.example.lab05.model.Product;
import com.example.lab05.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/product")
@MultipartConfig
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        productDAO = new ProductDAO();
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            Long productId = Long.parseLong(req.getParameter("id"));
            productDAO.remove(productId);
        }

        if ("edit".equals(action)) {
            Long productId = Long.parseLong(req.getParameter("id"));
            Product product = productDAO.get(productId);
            req.setAttribute("id", product.getId());
            req.setAttribute("name", product.getName());
            req.setAttribute("price", product.getPrice());
        }

        req.setAttribute("AllProducts", productDAO.getAll());
        req.getRequestDispatcher("/product.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            double price = Double.parseDouble(req.getParameter("price"));
            String id = req.getParameter("id");
            if(id != null) {
                Long productId = Long.parseLong(req.getParameter("id"));

                Product product = productDAO.get(productId);
                product.setName(name);
                product.setPrice(price);
                productDAO.update(product);
            } else {
                productDAO = new ProductDAO();
                productDAO.add(new Product(name, price));
                req.setAttribute("successMessage", "Product added successfully");
            }
            req.setAttribute("AllProducts", productDAO.getAll());
            req.getRequestDispatcher("/product.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Failed to add the product");
            System.out.println(e.getMessage());
        }
    }
}
