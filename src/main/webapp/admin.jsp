<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 21.11.2024
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }
        h1 {
            color: #333;
        }
        a {
            display: block;
            padding: 10px;
            margin: 10px 0;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Admin Panel</h1>
    <a href="${pageContext.request.contextPath}/admin/passenger">Пассажиры</a>
    <a href="${pageContext.request.contextPath}/airport">Аэропорты</a>
    <a href="${pageContext.request.contextPath}/flight">Рейсы</a>
    <a href="${pageContext.request.contextPath}/admin/ticket">Билеты</a>
</div>
</body>
</html>
