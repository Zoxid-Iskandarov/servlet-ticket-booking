<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 21.11.2024
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
    <p><a href="${pageContext.request.contextPath}/admin/passenger">Пассажиры</a></p>
    <p><a href="${pageContext.request.contextPath}/airport">Аэропорты</a></p>
    <p><a href="${pageContext.request.contextPath}/flight">Рейсы</a></p>
    <p><a href="${pageContext.request.contextPath}/admin/ticket">Билеты</a></p>
</body>
</html>
