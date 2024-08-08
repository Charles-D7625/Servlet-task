<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Product Categories</title>
</head>
<body>
    <h1>Product Categories</h1>
    <a href="${pageContext.request.contextPath}/product-category/json">View JSON</a>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Type</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="productCategory" items="${listProductCategory}">
                <tr>
                    <td>${productCategory.id}</td>
                    <td>${productCategory.name}</td>
                    <td>${productCategory.type.getType()}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
