<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Product List</title>
</head>
<body>
    <h1>Product List</h1>
    <a href="${pageContext.request.contextPath}/product/new">New Product</a>
    <a href="${pageContext.request.contextPath}/product/json">View JSON</a>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Available</th>
            <th>Order ID</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="product" items="${listProduct}">
            <tr>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.quantity}</td>
                <td>${product.available}</td>
                <td>${product.orderId}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/product/edit?id=${product.id}">Edit</a>
                    <a href="${pageContext.request.contextPath}/product/delete?id=${product.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>