<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><c:choose><c:when test="${not empty product.id}">Edit Product</c:when><c:otherwise>New Product</c:otherwise></c:choose></title>
</head>
<body>
    <h1><c:choose><c:when test="${not empty product.id}">Edit Product</c:when><c:otherwise>New Product</c:otherwise></c:choose></h1>
    <form action="<c:choose><c:when test='${not empty product.id}'>update</c:when><c:otherwise>insert</c:otherwise></c:choose>" method="post">
        <c:if test="${not empty product.id}">
            <input type="hidden" name="id" value="${product.id}" />
        </c:if>
        <p>Name: <input type="text" name="name" value="${product.name != null ? product.name : ''}" /></p>
        <p>Price: <input type="text" name="price" value="${product.price != null ? product.price : ''}" /></p>
        <p>Quantity: <input type="text" name="quantity" value="${product.quantity != null ? product.quantity : ''}" /></p>
        <p>Available: <input type="checkbox" name="available" value="true" ${product.available ? "checked" : ""} /></p>
        <p><input type="submit" value="<c:choose><c:when test='${not empty product.id}'>Update</c:when><c:otherwise>Create</c:otherwise></c:choose>" /></p>
    </form>
</body>
</html>