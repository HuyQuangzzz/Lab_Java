<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Sản phẩm</title>
</head>
<body>
<h1>Danh sách Sản phẩm</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Tên Sản phẩm</th>
        <th>Giá</th>
    </tr>
    <c:forEach items="${productList}" var="product">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
        </tr>
    </c:forEach>
</table>

<h2>Thêm Sản phẩm</h2>
<form action="addProduct" method="post">
    ID: <input type="text" name="id"><br>
    Tên Sản phẩm: <input type="text" name="name"><br>
    Giá: <input type="text" name="price"><br>
    <input type="submit" value="Thêm">
</form>
</body>
</html>
