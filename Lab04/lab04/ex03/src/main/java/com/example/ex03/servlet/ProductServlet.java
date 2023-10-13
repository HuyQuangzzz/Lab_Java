package com.example.ex03.servlet;

import com.example.ex03.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import java.io.PrintWriter;


@WebServlet("/ProductServices")
public class ProductServlet extends HttpServlet {
    private List<Product> productList = new ArrayList<>();
    public ProductServlet() {
        productList.add(new Product(1, "iPhone 11", 549.0));
        productList.add(new Product(3, "iPhone 13 Pro", 999.0));
    }
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        productList.add(new Product(1, "iPhone 11", 549.0));
//        productList.add(new Product(3, "iPhone 13 Pro", 999.0));
//    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam != null) {

            int id = Integer.parseInt(idParam);
            Product foundProduct = null;
            for (Product product : productList) {
                if (product.getId() == id) {
                    foundProduct = product;
                    break;
                }
            }
            if (foundProduct != null) {
                List<Product> productResult = new ArrayList<>();
                productResult.add(foundProduct);
                String jsonResponse = buildJsonResponse(1, "Tìm thấy sản phẩm với mã số "+ idParam, productResult);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse);
            } else {
                List<Product> productResult = new ArrayList<>();
                String jsonResponse = errorJsonResponse(2, "Không tìm thấy sản phẩm nào với mã số "+ idParam);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse);
            }
        } else {
            response.setContentType("application/json");
            String jsonResponse = buildJsonResponse(0, "Đọc sản phẩm thành công", productList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        String idParam = request.getParameter("id");
//        String name = request.getParameter("name");
//        String priceParam = request.getParameter("price");
//
//        int productId = Integer.parseInt(idParam);
//        double price = Double.parseDouble(priceParam);
//
//        if (isProductIdExists(productId)) {errorJsonResponse(2, "Id Sản phẩm đã tồn tại ");
//            return;
//        }
//
//
//        Product newProduct = new Product(productId, name, price);
//        productList.add(newProduct);
//        errorJsonResponse(0, "Thêm sản phẩm thành công ");

//        try (JsonReader reader = Json.createReader(request.getInputStream())) {
//            JsonObject jsonObject = reader.readObject();
//
//            // Extract data from the JsonObject
//            String name = jsonObject.getString("name");
//            double price = jsonObject.getJsonNumber("price").doubleValue();
//
//            // Create a new Product object and add it to your list
//            Product newProduct = new Product(name, price);
//            products.add(newProduct);
//        } catch (Exception e) {
//            // Handle any exception that may occur during JSON parsing
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().println("{\"error\": \"Failed to parse JSON request body.\"}");
//            return;
//        }

    }

    private boolean isProductIdExists(int productId) {
        for(Product i : productList){
            if(i.getId() == productId){
                return true;
            }
        }
        return false;
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String priceParam = request.getParameter("price");

        if (idParam == null || name == null || priceParam == null) {
            sendJsonResponse(response, 2, "Missing parameters", null);
            return;
        }

        int productId;
        double price;

        try {
            productId = Integer.parseInt(idParam);
            price = Double.parseDouble(priceParam);
        } catch (NumberFormatException e) {
            sendJsonResponse(response, 3, "Invalid parameter format", null);
            return;
        }

        Product existingProduct = getProductById(productId);
        if (existingProduct != null) {
            existingProduct.setName(name);
            existingProduct.setPrice(price);
            sendJsonResponse(response, 0, "Product updated successfully", existingProduct);
        } else {
            sendJsonResponse(response, 5, "Product not found for update", null);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null) {
            sendJsonResponse(response, 2, "Missing parameter (id)", null);
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            sendJsonResponse(response, 3, "Invalid parameter format", null);
            return;
        }

        Product productToRemove = getProductById(productId);
        if (productToRemove != null) {
            productList.remove(productToRemove);
            sendJsonResponse(response, 0, "Product deleted successfully", productToRemove);
        } else {
            sendJsonResponse(response, 6, "Product not found for deletion", null);
        }
    }
    private Product getProductById(int id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    private void sendJsonResponse(HttpServletResponse response, int id, String message, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("code", id);
        jsonResponse.put("message", message);
        jsonResponse.put("data", data);

        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();

    }
    private String buildJsonResponse(int code, String message, List<Product> products) {
        StringBuilder jsonResponse = new StringBuilder();
        jsonResponse.append("{\n");
        jsonResponse.append("    \"code\": ").append(code).append(",\n");
        jsonResponse.append("    \"message\": \"").append(message).append("\",\n");
        jsonResponse.append("    \"data\": [\n");

        boolean firstProduct = true;
        for (Product product : products) {
            if (!firstProduct) {
                jsonResponse.append(",\n");
            }
            jsonResponse.append("        {\n");
            jsonResponse.append("            \"id\": ").append(product.getId()).append(",\n");
            jsonResponse.append("            \"name\": \"").append(product.getName()).append("\",\n");
            jsonResponse.append("            \"price\": ").append(product.getPrice()).append("\n");
            jsonResponse.append("        }");
            firstProduct = false;
        }

        jsonResponse.append("\n    ]\n");
        jsonResponse.append("}");

        return jsonResponse.toString();
    }
    private String errorJsonResponse(int code, String message) {
        StringBuilder jsonResponse = new StringBuilder();
        jsonResponse.append("{\n");
        jsonResponse.append("    \"code\": ").append(code).append(",\n");
        jsonResponse.append("    \"message\": \"").append(message).append("\",\n");
        jsonResponse.append("}");

        return jsonResponse.toString();
    }




}