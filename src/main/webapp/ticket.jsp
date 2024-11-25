<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 24.11.2024
  Time: 2:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ticket page</title>
</head>
<body>
    <h1>Актуальные билеты</h1>
    <c:if test="${not empty requestScope.activeTickets}">
        <table border="1">
            <thead>
            <tr>
                <th>ID рейса</th>
                <th>Номер места</th>
                <th>Класс обслуживания</th>
                <th>Допустимый багаж</th>
                <th>Ручная кладь</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="ticket" items="${requestScope.activeTickets}">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/flight?id=${ticket.flightId}">
                            ${ticket.flightId}
                        </a>
                    </td>
                    <td>${ticket.seatNumber}</td>
                    <td>${ticket.serviceClass}</td>
                    <td>${ticket.baggageAllowance}</td>
                    <td>${ticket.handBaggageAllowance}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <h1>Все билеты</h1>
    <c:if test="${not empty requestScope.tickets}">
        <table border="1">
            <thead>
            <tr>
                <th>ID рейса</th>
                <th>Номер места</th>
                <th>Класс обслуживания</th>
                <th>Допустимый багаж</th>
                <th>Ручная кладь</th>
                <th>Статус</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="ticket" items="${requestScope.tickets}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/flight?id=${ticket.flightId}">
                            ${ticket.flightId}
                        </a>
                    </td>
                    <td>${ticket.seatNumber}</td>
                    <td>${ticket.serviceClass}</td>
                    <td>${ticket.baggageAllowance}</td>
                    <td>${ticket.handBaggageAllowance}</td>
                    <td><c:choose>
                        <c:when test="${ticket.canceled}">Отменен</c:when>
                        <c:otherwise>Активен</c:otherwise>
                    </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
