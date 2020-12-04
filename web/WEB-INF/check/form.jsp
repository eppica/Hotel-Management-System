<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/form.css">
    <title>Checks</title>
    <meta name="viewport" content="width=device-width, user-scalable=0">
</head>
<body>
<form action="/checks" id="form" <c:if test="${check == null}">method="POST" </c:if> >
    <h1><c:choose><c:when test="${check == null}">New</c:when><c:otherwise>Edit</c:otherwise></c:choose> Check</h1>
    <label for="check">Check</label>
    <input type="datetime-local" name="check" id="check" autocomplete="off" value="${check.getCheckTime()}">
    <label for="id_staff">Staff</label>
    <select name="id_staff" id="id_staff">
        <option disabled selected value></option>
        <c:forEach items="${staffList}" var="staff">
            <option value="${staff.getId()}" <c:if test="${staff.getId() == check.getStaff().getId()}"> selected </c:if>>${staff.getName()}</option>
        </c:forEach>
    </select>
    <label for="id_booking">Booking</label>
    <select name="id_booking" id="id_booking">
        <option disabled selected value></option>
        <c:forEach items="${bookingList}" var="booking">
            <option value="${booking.getId()}" <c:if test="${booking.getId() == check.getBooking().getId()}"> selected </c:if>>${booking.getId()}</option>
        </c:forEach>
    </select>
    <label>Status
        <input type="checkbox" id="status" checked="checked" name="status">
        <div class="slider"><div class="round"></div></div>
    </label>

    <input type="submit" value="SUBMIT" id="button">
</form>
</body>
<script>
    function send(e){
        e.preventDefault();
        let url = "/checks/${check.getId()}";

        fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'check': document.getElementById("check").value,
                'id_staff': document.getElementById("id_staff").value,
                'id_booking': document.getElementById("id_booking").value,
                'status': document.getElementById("status").checked
            }),
        }).then(resp => {   window.location.href = url });
    }

    if(document.getElementById("form").method !== "post"){
        document.getElementById("form").addEventListener("submit", send);
    }

</script>
</html>