
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/one.css">
    <title>Reports</title>
    <meta name="viewport" content="width=device-width, user-scalable=0">
</head>
<body id="body">
<c:import url="/WEB-INF/header/main.jsp"/>
<div class="content">
    <div class="model">
        <h1>Reports</h1>
    </div>
    <div class="list">
        <h2 class="title">Filtered Reports</h2>
        <ul>
            <li onclick="openModalPayment()">
                <span>Payment Report</span>
            </li>
            <li onclick="openModalRoom()">
                <span>Rooms Report</span>
            </li>
            <li onclick="openModalStaff()">
                <span>Staff Report</span>
            </li>
            <li onclick="openModalRoomType()">
                <span>Room Types Report</span>
            </li>
            <li onclick="openModalArrivalDeparture()">
                <span>Arrivals and Departures Report</span>
            </li>
        </ul>
        <h2 class="title">Full Reports</h2>
        <ul>
            <li onclick="bookingReport()">
                <span>Bookings Report</span>
            </li>

            <li onclick="guestReport()">
                <span>Guests Report</span>
            </li>
        </ul>
    </div>
</div>


<div class="modal" id="modal-room">
    <div class="modal-content params">
        <div class="modal-header">
            <h1>Rooms Report</h1>
        </div>
        <div class="modal-body">
            <form action="/reports/rooms" method="POST">
                <label for="id_room_type">Room Type</label>
                <select name="id_room_type" id="id_room_type" required>
                    <option disabled selected value></option>
                    <c:forEach items="${roomTypeList}" var="roomType">
                        <option value="${roomType.getId()}" <c:if test="${roomType.getId() == room.getRoomType().getId()}"> selected </c:if>>${roomType.getName()}</option>
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

<div class="modal" id="modal-room-type">
    <div class="modal-content params">
        <div class="modal-header">
            <h1>Room Types Report</h1>
        </div>
        <div class="modal-body">
            <form action="/reports/roomtypes" method="POST">
                <label for="min_daily_price">Minimum Daily Price</label>
                <input type="number" name="min_daily_price" id="min_daily_price" min="0.00" max="10000.00" step="0.01">
                <label for="max_daily_price">Maximum Daily Price</label>
                <input type="number" name="max_daily_price" id="max_daily_price" min="0.00" max="10000.00" step="0.01">
                <div class="modal-footer">
                    <button onclick="cancel()" type="button">Cancel</button>
                    <input type="submit" value="Submit">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal" id="modal-staff">
    <div class="modal-content params">
        <div class="modal-header">
            <h1>Staff Report</h1>
        </div>
        <div class="modal-body">
            <form action="/reports/staff" method="POST">
                <label for="access_level">Access Level</label>
                <select name="access_level" id="access_level" required>
                    <option disabled selected value></option>
                    <c:forEach items="${accessLevelList}" var="accessLevel">
                        <option value="${accessLevel}" <c:if test="${accessLevel == staff.getAccessLevel()}"> selected </c:if>>${accessLevel}</option>
                    </c:forEach>
                </select>
                <div class="modal-footer">
                    <button onclick="cancel()" type="button">Cancel</button>
                    <input type="submit" value="Submit">
                </div>
            </form>
        </div>
    </div>
</div>


<div class="modal" id="modal-payment">
    <div class="modal-content params">
        <div class="modal-header">
            <h1>Payments Report</h1>
        </div>
        <div class="modal-body">
            <form action="/reports/payments" method="POST">
                <label for="initial">Initial</label>
                <input type="date" name="initial" id="initial" autocomplete="off" >
                <label for="final">Final</label>
                <input type="date" name="final" id="final" autocomplete="off" >

                <div class="modal-footer">
                    <button onclick="cancel()" type="button">Cancel</button>
                    <input type="submit" value="Submit">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal" id="modal-arrival-departure">
    <div class="modal-content params">
        <div class="modal-header">
            <h1>Arrivals and Departures Report</h1>
        </div>
        <div class="modal-body">
            <form action="/reports/arrivaldeparture" method="POST">
                <label for="initial2">Initial</label>
                <input type="date" name="initial" id="initial2" autocomplete="off" >
                <label for="final2">Final</label>
                <input type="date" name="final" id="final2" autocomplete="off" >
                <div class="modal-footer">
                    <button onclick="cancel()" type="button">Cancel</button>
                    <input type="submit" value="Submit">
                </div>
            </form>
        </div>
    </div>
</div>



</body>
<script>
    let modalRoom = document.getElementById("modal-room");
    let modalStaff = document.getElementById("modal-staff");
    let modalRoomType = document.getElementById("modal-room-type");
    let modalPayment = document.getElementById("modal-payment");
    let modalArrivalDeparture = document.getElementById("modal-arrival-departure");

    function openModalRoom() {
        modalRoom.style.display = "flex";
    }

    function openModalStaff() {
        modalStaff.style.display = "flex";
    }

    function openModalRoomType() {
        modalRoomType.style.display = "flex";
    }

    function openModalPayment() {
        modalPayment.style.display = "flex";
    }

    function openModalArrivalDeparture() {
        modalArrivalDeparture.style.display = "flex";
    }

    document.getElementById("body").addEventListener("submit", () => {
        modalRoom.style.display = "none";
        modalStaff.style.display = "none";
        modalRoomType.style.display = "none";
        modalPayment.style.display = "none";
        modalArrivalDeparture.style.display = "none";
    });

    function cancel(){
        modalRoom.style.display = "none";
        modalStaff.style.display = "none";
        modalRoomType.style.display = "none";
        modalPayment.style.display = "none";
        modalArrivalDeparture.style.display = "none";
    }

    function guestReport() {
        window.location = "/reports/guests";
    }

    function bookingReport() {
        window.location = "/reports/bookings";
    }
</script>
</body>
</html>
