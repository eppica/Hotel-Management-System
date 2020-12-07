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
<form method="POST" action="/auth/login" id="form-login">
    <h1>Login</h1>
    <span id="error">${errorMessage}</span>
    <label for="login">Login</label>
    <input type="text" name="login" id="login" autofocus required>
    <label for="password">Password</label>
    <input type="password" id="password" name="password" required>
    <div class="submit">
        <input type="submit" value="Login">
    </div>
</form>

<script>

    const error = document.getElementById("error");

    if(error.innerHTML != ""){
        error.style.display = "inline";
    }
</script>

</body>
</html>