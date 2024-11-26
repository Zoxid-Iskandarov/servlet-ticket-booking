<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 17.11.2024
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
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
        form {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="email"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"], .button-link {
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
        }
        input[type="submit"]:hover, .button-link:hover {
            background-color: #0056b3;
        }
        .error {
            color: red;
            font-size: 14px;
            text-align: center;
            margin-bottom: 15px;
        }
        .footer {
            text-align: center;
            margin-top: 15px;
        }
        form {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            gap: 10px; /* Зазор между элементами */
        }

        button {
            width: 100%; /* Сделать кнопки одинаковой ширины */
            max-width: 300px; /* Ограничить максимальную ширину */
            padding: 10px; /* Добавить внутренние отступы */
            font-size: 16px; /* Размер текста на кнопке */
        }

        button[type="submit"] {
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
        }

        button[type="submit"]:hover {
            background-color: #0056b3;
        }

        button[type="button"] {
            background-color: #f0f0f0;
            color: #007BFF;
            border: 1px solid #007BFF;
        }

        button[type="button"]:hover {
            background-color: #e6e6e6;
        }
    </style>
</head>
<body>
<main>
    <form action="${pageContext.request.contextPath}/auth" method="post">
        <h1>Authorization</h1>
        <%
            if (response.getStatus() == 401) {
        %>
        <div class="error">Неверный логин или пароль</div>
        <%
            }
        %>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="Log In">
        <div class="footer">
            <a href="${pageContext.request.contextPath}/signUp" class="button-link">Sign Up</a>
        </div>
    </form>
</main>
</body>
</html>
