<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/one.css">
    <title>Room</title>
    <meta name="viewport" content="width=device-width, user-scalable=0">
</head>
<body>
<c:import url="/WEB-INF/header/main.jsp"/>
<div class="content">
    <div class="model">
        <h1>Room</h1>
        <h3>${room.getAvailability()}</h3>
    </div>
    <c:if test="${allowed == true}">
    <div class="edit">
        <button class="delete" onclick="openModal(${room.getNumber()})">Delete</button>
        <button onclick="window.location.href='/rooms/${room.getId()}/edit';">Edit</button>
    </div>
    </c:if>
    <div class="about">
        <h2>About</h2>
        <div class="property">
            <span class="label">ID</span>
            <span class="data">${room.getId()}</span>
        </div>
        <div class="property">
            <span class="label">Number</span>
            <span class="data">${room.getNumber()}</span>
        </div>
        <div class="property link" onclick="window.location.href='/roomTypes/${room.getRoomType().getId()}';">
            <span class="label">Room Type</span>
            <span class="data">${room.getRoomType().getName()}</span>
        </div>
    </div>
    <c:if test="${booking != null}">
    <div class="booking-info about" onclick="window.location.href='/bookings/${booking.getId()}';">
        <h2>Booking</h2>
        <div class="property">
            <span class="label">ID</span>
            <span class="data">${booking.getId()}</span>
        </div>
        <div class="property">
            <span class="label">Arrival</span>
            <span class="data">${booking.getArrival()}</span>
        </div>
        <div class="property">
            <span class="label">Departure</span>
            <span class="data">${booking.getDeparture()}</span>
        </div>
        <div class="property">
            <span class="label">Guest</span>
            <span class="data">${booking.getGuest().getName()}</span>
        </div>
    </div>
    </c:if>
</div>
<div class="modal" id="modal-delete">
    <div class="modal-content">
        <div class="modal-header">
            <h1>Delete</h1>
        </div>
        <div class="modal-body" id="sure">
        </div>
        <div class="modal-footer">
            <button onclick="cancel()" type="button">Cancel</button>
            <button onclick="link(${room.getId()})" class="cancel"> Delete </button>
        </div>
    </div>
</div>
</body>
<script>
    let modal = document.getElementById("modal-delete");
    function openModal(room) {
        modal.style.display = "flex";
        document.getElementById("sure").innerHTML = "Delete room " + room + "?";
    }
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };

    function cancel(){
        modal.style.display = "none";
    }


    function link(id) {
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
