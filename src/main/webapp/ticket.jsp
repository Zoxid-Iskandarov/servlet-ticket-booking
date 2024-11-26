<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 24.11.2024
  Time: 2:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ticket Information</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background: #fff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ddd;
        }
        th {
            background-color: #007BFF;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
        a {
            color: #007BFF;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .no-data {
            text-align: center;
            color: #777;
            font-size: 18px;
            margin: 20px 0;
        }
    </style>
</head>
<body>

<c:if test="${not empty requestScope.activeTickets}">
    <h1>Актуальные билеты</h1>
    <table>
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
<c:if test="${empty requestScope.activeTickets}">
    <div class="no-data">Нет актуальных билетов.</div>
</c:if>

<c:if test="${not empty requestScope.tickets}">
    <h1>Все билеты</h1>
    <table>
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
                <td>
                    <a href="${pageContext.request.contextPath}/flight?id=${ticket.flightId}">
                            ${ticket.flightId}
                    </a>
                </td>
                <td>${ticket.seatNumber}</td>
                <td>${ticket.serviceClass}</td>
                <td>${ticket.baggageAllowance}</td>
                <td>${ticket.handBaggageAllowance}</td>
                <td>
                    <c:choose>
                        <c:when test="${ticket.canceled}">Отменен</c:when>
                        <c:otherwise>Активен</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty requestScope.tickets}">
    <div class="no-data">Нет доступных билетов.</div>
</c:if>

</body>
</html>
