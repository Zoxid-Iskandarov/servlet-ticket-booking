<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 17.11.2024
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login form</title>
</head>
<body>
<%
    if (response.getStatus() == 401) {
        out.println("<h1>Неверный логин или пароль</h1>");
    }
%>

    <div>
        <form action="${pageContext.request.contextPath}/auth" method="post">
            <h1>Authorization</h1>
            <br>
            Email: <input type="email" name="email" required>
            <br>
            <br>
            Password: <input type="password" name="password" required>
            <br>
            <br>
            <input type="submit" value="Log In">
            <a href="${pageContext.request.contextPath}/signUp">
                <input type="button" value="Sign Up">
            </a>
        </form>
    </div>
</body>
</html>
