<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit Order Detail</title>
</head>
<body>
    <h2>Edit Order Detail</h2>
    <form action="${pageContext.request.contextPath}/order-detail/update" method="post">
        <input type="hidden" name="id" value="${orderDetail.id}" />
        <p>Order Status:
            <select name="orderStatus" required>
                <c:forEach var="status" items="${orderStatuses}">
                    <option value="${status}" ${orderDetail.orderStatus == status ? 'selected' : ''}>${status.getStatus()}</option>
                </c:forEach>
            </select>
        </p>
        <p>Total Amount: <input type="text" name="totalAmount" value="${orderDetail.totalAmount}" required/></p>
        <input type="submit" value="Update" />
    </form>

    <a href="${pageContext.request.contextPath}/order-detail">Back to List</a>
</body>
</html>
