<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 17.11.2024
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Registration form</title>
  </head>
  <body>
  <%
    if (response.getStatus() == 401) {
      out.println("Что то пошло не так");
    }
  %>
    <form action="${pageContext.request.contextPath}/signUp" method="post">
      <h1>Registration</h1>
      <br>
      Email: <input type="email" name="email" required>
      <br>
      <br>
      Password: <input type="password" name="password" required>
      <br>
      <br>
      FirstName: <input type="text" name="firstName" required>
      <br>
      <br>
      LastName: <input type="text" name="lastName" required>
      <br>
      <br>
      Patronymic: <input type="text" name="patronymic" required>
      <br>
      <br>
      <input type="submit" value="Sign Up">
    </form>
  </body>
</html>
