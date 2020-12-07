<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/find.css">
    <title>Rooms</title>
    <meta name="viewport" content="width=device-width, user-scalable=0">
</head>
<body>
<c:import url="/WEB-INF/header/main.jsp"/>
<div class="content">
    <h1>Rooms</h1>
    <div class="over">
        <table>
            <thead>
                <tr><th>ID</th><th>Number</th><th>Room Type</th><th>Availability</th></tr>
            </thead>
            <tbody>
                <c:forEach items="${roomList}" var="room">
                    <tr onclick="window.location.href='/rooms/${room.getId()}';"><td>${room.getId()}</td><td>${room.getNumber()}</td><td>${room.getRoomType().getName()}</td><td>${room.getAvailability()}</td></tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    </table>
    <c:if test="${allowed == true}">
    <div class="submit">
        <button onclick="window.location.href='/rooms/new';">New Room</button>
    </div>
    </c:if>
</div>

</body>

<script>
    function link(id) {
        console.log(id);
        let url = "/rooms/"+ id;

        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
        }).then(resp => {   window.location.href = "/rooms" });
    }
</script>
</html>
