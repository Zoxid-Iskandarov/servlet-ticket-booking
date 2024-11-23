<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 23.11.2024
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Airport page</title>
</head>
<body>
    <c:if test="${not empty requestScope.airport}">
        <h1>${requestScope.airport}</h1>
    </c:if>

    <c:if test="${not empty requestScope.airports}">
        <table>
            <tr>
                <th>Коде</th>
                <th>Название</th>
                <th>Адрес</th>
            </tr>
            <c:forEach var="airport" items="${requestScope.airports}">
                <tr>
                    <td>${airport.code}</td>
                    <td>${airport.name}</td>
                    <td>${airport.address}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
