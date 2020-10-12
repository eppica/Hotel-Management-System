<%--
  Created by IntelliJ IDEA.
  User: Lavinia
  Date: 10/12/2020
  Time: 12:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/one.css">
    <title>Reports</title>
    <meta name="viewport" content="width=device-width, user-scalable=0">
</head>
<body>
<c:import url="/WEB-INF/header/main.jsp"/>
<div class="content">
    <div class="model">
        <h1>Reports</h1>
    </div>
    <div class="list">
        <ul>
            <li>
                <span>Payment Report</span>
            </li>
            <li>
                <span>Guests Report</span>
            </li>
            <li onclick="openModalRoom()">
                <span >Rooms Report</span>
            </li>
            <li>
                <span>Staff Report</span>
            </li>
            <li>
                <span>Room Types Report</span>
            </li>
            <li>
                <span>Bookings Report</span>
            </li>
        </ul>
    </div>
</div>

<div class="modal" id="modal-room">
    <div class="modal-content params">
        <div class="modal-header">
            <h1 id="title">Rooms Report</h1>
        </div>
        <div class="modal-body">
            <form action="/reports/rooms" id="form" method="GET">
                <label for="id_room_type">Room Type</label>
                <select name="id_room_type" id="id_room_type" required>
                    <option disabled selected value></option>
                    <c:forEach items="${roomTypeList}" var="roomType">
                        <option value="${roomType.getId()}" <c:if test="${roomType.getId() == room.getIdRoomType()}"> selected </c:if>>${roomType.getName()}</option>
                    </c:forEach>
                </select>
                <div class="modal-footer">
                    <button onclick="cancel()" type="button">Cancel</button>
                    <input type="submit" value="Submit" id="button">
                </div>
            </form>
        </div>
    </div>
</div>


</body>
<script>
    let modalRoom = document.getElementById("modal-room");

    function openModalRoom() {
        modalRoom.style.display = "flex";
    }
    window.onclick = function(event) {
        if (event.target === modalRoom) {
            modalRoom.style.display = "none";
        }
    };

    function cancel(){
        modalRoom.style.display = "none";
    }
</script>
</body>
</html>
