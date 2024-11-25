<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 23.11.2024
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Flight Information</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .info-container {
            max-width: 800px;
            margin: 20px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .info-container p {
            font-size: 16px;
            color: #555;
            margin: 10px 0;
        }
        table {
            width: 100%;
            max-width: 1200px;
            margin: 20px auto;
            border-collapse: collapse;
            background: #fff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
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
    </style>
</head>
<body>
<c:if test="${not empty requestScope.flight}">
    <div class="info-container">
        <h1>Информация о рейсе</h1>
        <table>
            <thead>
            <tr>
                <th>Дата отъезда</th>
                <th>Дата прибытия</th>
                <th>Аэропорт отъезда</th>
                <th>Аэропорт прибытия</th>
                <th>Общее количество мест</th>
                <th>Доступное количество мест</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${requestScope.flight.departureDate}</td>
                <td>${requestScope.flight.arrivalDate}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/airport?id=${requestScope.flight.departureAirportId}">
                            ${requestScope.flight.departureAirportId}
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/airport?id=${requestScope.flight.arrivalAirportId}">
                            ${requestScope.flight.arrivalAirportId}
                    </a>
                </td>
                <td>${requestScope.flight.totalSeats}</td>
                <td>${requestScope.flight.availableSeats}</td>
            </tr>
            </tbody>
        </table>
    </div>
</c:if>

<c:if test="${not empty requestScope.flights}">
    <h1>Список всех рейсов</h1>
    <table>
        <thead>
        <tr>
            <th>Дата отъезда</th>
            <th>Дата прибытия</th>
            <th>Аэропорт отъезда</th>
            <th>Аэропорт прибытия</th>
            <th>Общее количество мест</th>
            <th>Доступное количество мест</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="flight" items="${requestScope.flights}">
            <tr>
                <td>${flight.departureDate}</td>
                <td>${flight.arrivalDate}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/airport?id=${flight.departureAirportId}">
                            ${flight.departureAirportId}
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/airport?id=${flight.arrivalAirportId}">
                            ${flight.arrivalAirportId}
                    </a>
                </td>
                <td>${flight.totalSeats}</td>
                <td>${flight.availableSeats}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
