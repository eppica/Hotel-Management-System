<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="content">
    <h1 id="today"></h1>
    <button onclick="window.location.href='/bookings/new';" class="newRes">New Booking</button>
    <div class="arrivals"><h1 class="number">${arrivalList.size()}</h1>Arrivals</div>
    <div class="departures"><h1 class="number">${departureList.size()}</h1> Departures</div>
    <div class="over arrival">
        <table>
            <thead>
            <tr><th colspan="3">Arrivals</th></tr>
            </thead>
            <tbody>
            <c:forEach items="${arrivalList}" var="arrival">
            <tr><td onclick="window.location.href='/bookings/${arrival.getId()}';"> <span class="booking">${arrival.getId()}</span><br><span class="guest">${arrival.getGuest().getName()}</span> </td><td class="room" onclick="window.location.href='/bookings/${arrival.getId()}';">${arrival.getRoom().getNumber()}</td><td class="check" onclick="openModal(true, ${arrival.getId()}, ${arrival.getRoom().getNumber()})">Checkin</td>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="over departure">
        <table>
            <thead>
            <tr><th colspan="3">Departures</th></tr>
            </thead>
            <tbody>
            <c:forEach items="${departureList}" var="departure">
            <tr><td onclick="window.location.href='/bookings/${departure.getId()}';"> <span class="booking">${departure.getId()}</span><br><span class="guest">${departure.getGuest().getName()}</span> </td><td class="room" onclick="window.location.href='/bookings/${departure.getId()}';">${departure.getRoom().getNumber()}</td>
                <c:if test="${!paid.get(departure.id)}">
                    <td class='check' style='background-color:gray;' onclick="window.location='/bookings/${departure.getId()}';">Pay</td>
                <c:out value=""/></c:if>
                <c:if test="${paid.get(departure.id)}">
                    <td class='check' onclick='openModal(false, ${departure.getId()}, ${departure.getRoom().getNumber()})'>Checkout</td>
                </c:if>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="modal" id="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h1 id="title"></h1>
        </div>
        <div class="modal-body">
            <form action="/checks" id="form" method="POST">
                <label for="check">Timestamp</label>
                <input type="datetime-local" name="check" id="check" autocomplete="off" readonly>
                <label for="id_booking">Booking</label>
                <input type="text" name="id_booking" id="id_booking" readonly>
                <label for="room_number">Room</label>
                <input type="text" name="room_number" id="room_number" readonly>
                <input type="number" name="status" id="status" style="display: none">
                <input type="number" style="display: none" name="id_staff" id="id_staff" value="${sessionStaff.getId()}">
                <div class="modal-footer">
                    <button onclick="cancel()" type="button">Cancel</button>
                    <input type="submit" value="Submit" id="button">
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    let modal = document.getElementById("modal");
    let title = document.getElementById("title");
    let today = document.getElementById("today");
    function openModal(test, booking, room) {
        if(test){
            title.innerHTML = "Checkin";
            document.getElementById("status").value = 1;
        }else{
            title.innerHTML = "Checkout";
            document.getElementById("status").value = 0;
        }
        modal.style.display = "flex";
        document.getElementById("id_booking").value = booking;
        document.getElementById("room_number").value = room;
        let now = new Date();
        now.setHours(now.getHours() - 3); //because of the timezone
        document.getElementById("check").value = now.toISOString().substring(0, 16);
    }
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }

    function cancel(){
        modal.style.display = "none";
    }

    const monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];

    let now = new Date();
    let data = monthNames[now.getMonth()] +" " + now.getDate() + ", " + now.getFullYear();
    today.innerText = data;

    let hoursToMidnight = 24 - now.getHours();
    let minutesToNextHour = 60 - now.getMinutes();
    let secondsToNextMinute = 60 - now.getSeconds();

    let minutesToMidnight = (hoursToMidnight*60) + minutesToNextHour;

    let secondsToMidnight = (minutesToMidnight*60) + secondsToNextMinute;

    let millisecondsToMidnight = secondsToMidnight*1000;

    setTimeout(function() {
        window.location.reload();
    }, millisecondsToMidnight)

</script>