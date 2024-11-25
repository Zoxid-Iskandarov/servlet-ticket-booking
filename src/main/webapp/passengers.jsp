<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 20.11.2024
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registered Passengers</title>
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
        a {
            color: #007BFF;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .message {
            text-align: center;
            color: #666;
            font-size: 18px;
        }
    </style>
</head>
<body>
<h1>Зарегистрированные пассажиры</h1>
<c:if test="${not empty requestScope.passengersByUserId}">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Отчество</th>
            <th>Пол</th>
            <th>Дата рождения</th>
            <th>Паспортные данные</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="passenger" items="${requestScope.passengersByUserId}">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/ticket?id=${passenger.id}">
                            ${passenger.id}
                    </a>
                </td>
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
<c:if test="${empty requestScope.passengersByUserId}">
    <p class="message">Нет зарегистрированных пассажиров.</p>
</c:if>
</body>
</html>
