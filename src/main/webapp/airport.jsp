<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 23.11.2024
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Airport Information</title>
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
            max-width: 600px;
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
<c:if test="${not empty requestScope.airport}">
    <div class="info-container">
        <h1>Информация об аэропорте</h1>
        <p><strong>Код:</strong> ${requestScope.airport.code}</p>
        <p><strong>Название:</strong> ${requestScope.airport.name}</p>
        <p><strong>Адрес:</strong> ${requestScope.airport.address}</p>
    </div>
</c:if>

<c:if test="${not empty requestScope.airports}">
    <h1>Все аэропорты</h1>
    <table>
        <thead>
        <tr>
            <th>Код</th>
            <th>Название</th>
            <th>Адрес</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="airport" items="${requestScope.airports}">
            <tr>
                <td>${airport.code}</td>
                <td>${airport.name}</td>
                <td>${airport.address}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
