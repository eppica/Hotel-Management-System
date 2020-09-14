<%--
  Created by IntelliJ IDEA.
  User: Lavinha
  Date: 5/10/2020
  Time: 3:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=0">
    <link rel="stylesheet" href="../css/form.css">
    <title>Login</title>
</head>
<body>
<form method="POST" action="/auth/login">
    <h1>Login</h1>
    <label for="login">Login</label>
    <input type="text" name="login" id="login" autofocus>
    <label for="password">Password</label>
    <input type="password" id="password" name="password">
    <div class="submit">
        <input type="submit" value="Login">
    </div>
</form>
</body>
</html>