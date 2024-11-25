<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 22.11.2024
  Time: 22:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Passenger Info Page</title>
</head>
<body>
<c:if test="${not empty requestScope.passenger}">
    ${requestScope.passenger.firstName}
    ${requestScope.passenger.lastName}
    ${requestScope.passenger.patronymic}
    ${requestScope.passenger.gender}
    ${requestScope.passenger.birthDate}
    ${requestScope.passenger.passportData}
</c:if>
<c:if test="${not empty requestScope.passengers}">
    <h1>Все пассажиры</h1>
    <table>
        <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Отчество</th>
            <th>Пол</th>
            <th>Дата рождения</th>
            <th>Паспортные данные</th>
        </tr>
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
    </table>
</c:if>
</body>
</html>
