<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Product List</title>
</head>
<body>
    <h1>Product List</h1>
    <a href="new">New Product</a>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Available</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="product" items="${listProduct}">
            <tr>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.quantity}</td>
                <td>${product.available}</td>
                <td>
                    <form action="edit" method="get">
                        <input type="hidden" name="id" value="${product.id}" />
                        <input type="submit" value="Edit" />
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>