<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 20.11.2024
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>All the passengers form</title>
</head>
<body>
    <h1>Зарегистрированные пассажиры</h1>
    <c:if test="${not empty requestScope.passengersByUserId}">
        <table>
            <tr>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Отчество</th>
                <th>Пол</th>
                <th>Дата рождения</th>
                <th>Паспортные данные</th>
            </tr>
            <c:forEach var="passenger" items="${requestScope.passengersByUserId}">
                <tr>
                    <td>${passenger.firstName}</td>
                    <td>${passenger.lastName}</td>
                    <td>${passenger.patronymic}</td>
                    <td>${passenger.gender}</td>
                    <td>${passenger.birthDate}</td>
                    <td>${passenger.passportData}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
