<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 23.11.2024
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Flight page</title>
</head>
<body>
    <c:if test="${not empty requestScope.flight}">
        <table>
            <tr>
                <th>Дата отъезда</th>
                <th>Дата прибытия</th>
                <th>Id аэропорта отъезда</th>
                <th>Id аэропорта прибытия</th>
                <th>Общее количество мест</th>
                <th>Допустимое количество мест</th>
            </tr>
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
        </table>
    </c:if>

    <c:if test="${not empty requestScope.flights}">
        <table>
            <tr>
                <th>Дата отъезда</th>
                <th>Дата прибытия</th>
                <th>Id аэропорта отъезда</th>
                <th>Id аэропорта прибытия</th>
                <th>Общее количество мест</th>
                <th>Допустимое количество мест</th>
            </tr>
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
        </table>
    </c:if>
</body>
</html>
