<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 22.11.2024
  Time: 22:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Passenger Management</title>
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
        .passenger-info {
            max-width: 600px;
            margin: 20px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .passenger-info p {
            font-size: 16px;
            color: #555;
            margin: 10px 0;
        }
        table {
            width: 100%;
            max-width: 1000px;
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
<c:if test="${not empty requestScope.passenger}">
    <div class="passenger-info">
        <h1>Информация о пассажире</h1>
        <p><strong>Имя:</strong> ${requestScope.passenger.firstName}</p>
        <p><strong>Фамилия:</strong> ${requestScope.passenger.lastName}</p>
        <p><strong>Отчество:</strong> ${requestScope.passenger.patronymic}</p>
        <p><strong>Пол:</strong> ${requestScope.passenger.gender}</p>
        <p><strong>Дата рождения:</strong> ${requestScope.passenger.birthDate}</p>
        <p><strong>Паспортные данные:</strong> ${requestScope.passenger.passportData}</p>
    </div>
</c:if>

<c:if test="${not empty requestScope.passengers}">
    <h1>Все пассажиры</h1>
    <table>
        <thead>
        <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Отчество</th>
            <th>Пол</th>
            <th>Дата рождения</th>
            <th>Паспортные данные</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="passenger" items="${requestScope.passengers}">
            <tr>
                <td>${passenger.firstName}</td>
                <td>${passenger.lastName}</td>
                <td>${passenger.patronymic}</td>
                <td>${passenger.gender}</td>
                <td>${passenger.birthDate}</td>
                <td>${passenger.passportData}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
