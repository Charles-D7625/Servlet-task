<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Order Details</title>
</head>
<body>
<h2>Order Details</h2>
<a href="new">Create New Order Detail</a>
<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Order status</th>
            <th>Total Amount</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="orderDetail" items="${listOrderDetail}">
            <tr>
                <td>${orderDetail.id}</td>
                <td>${orderDetail.orderStatus.status}</td>
                <td>${orderDetail.totalAmount}</td>
                <td>
                    <a href="edit?id=${orderDetail.id}">Edit</a>
                    <a href="delete?id=${orderDetail.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>