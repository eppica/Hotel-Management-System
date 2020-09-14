<%--
  Created by IntelliJ IDEA.
  User: Lavinha
  Date: 5/3/2020
  Time: 12:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/form.css">
    <title>Guests</title>
    <meta name="viewport" content="width=device-width, user-scalable=0">
</head>
<body>
<header>
    <div class="logo" onclick="window.location.href='/dashboard';">
        <h1>HMS</h1>
    </div>
    <nav>
        <a href="/bookings/">Bookings</a><a href="/guests/">Guests</a></a><a href="/rooms/">Rooms</a><a href="/roomTypes/">Room Types</a>
    </nav>
    <div class="user">
        <h2>${sessionStaff.getName()}</h2>
        <a href="/auth/logout">Logout</a>
    </div>
</header>
<div class="content">
    <form action="/guests" id="form" <c:if test="${guest == null}">method="POST" </c:if> >
        <h1><c:choose><c:when test="${guest == null}">New</c:when><c:otherwise>Edit</c:otherwise></c:choose> Guest</h1>
        <label for="name">Name</label>
        <input type="text" name="name" id="name" autocomplete="off" autofocus="autofocus" value="${guest.getName()}">
        <label for="document">Document</label>
        <input type="text" name="document" id="document" autocomplete="off" value="${guest.getDocument()}">
        <label for="birth_date">Birth Date</label>
        <input type="date" name="birth_date" id="birth_date" autocomplete="off" value="${guest.getBirthDate()}">
        <label for="email">E-mail</label>
        <input type="email" name="email" id="email" autocomplete="off" value="${guest.getEmail()}">
        <label for="phone_number">Phone Number</label>
        <input type="tel" name="phone_number" id="phone_number" autocomplete="off" value="${guest.getPhoneNumber()}">

        <div class="submit">
            <button onclick="window.history.go(-1);" type="button">Cancel</button>
            <input type="submit" value="Submit" id="button">
        </div>
    </form>
</div>
</body>
<script>
    function send(e){
        e.preventDefault();
        let url = "/guests/${guest.getId()}";

        fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'name': document.getElementById("name").value,
                'document': document.getElementById("document").value,
                'birth_date': document.getElementById("birth_date").value,
                'email': document.getElementById("email").value,
                'phone_number': document.getElementById("phone_number").value
            }),
        }).then(resp => {   window.location.href = url });
    }

    if(document.getElementById("form").method !== "post"){
        document.getElementById("form").addEventListener("submit", send);
    }

</script>
</html>
