<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/form.css">
    <title>Bookings</title>
    <meta name="viewport" content="width=device-width, user-scalable=0">
</head>
<body>
<c:import url="/WEB-INF/header/main.jsp"/>
<div class="content">
    <form action="/bookings" id="form" <c:if test="${booking == null}">method="POST" </c:if> >
        <h1><c:choose><c:when test="${booking == null}">New</c:when><c:otherwise>Edit</c:otherwise></c:choose> Booking</h1>
        <label for="arrival">Arrival</label>
        <input type="date" name="arrival" id="arrival" autocomplete="off" min="${minDateArrival}" value="${booking.getArrival()}" required>
        <label for="departure">Departure</label>
        <input type="date" name="departure" id="departure" autocomplete="off" value="${booking.getDeparture()}" required>
        <div class="split">
            <label for="room_type">Room Type</label>
            <label for="id_room">Number</label>
            <select name="room_type" id="room_type"<c:if test="${booking == null}">disabled </c:if> required>
                <c:if test="${booking == null}"><option disabled selected value></option></c:if>
                <c:forEach items="${roomTypeList}" var="roomType">
                    <option value="${roomType.getId()}" <c:if test="${booking.getRoom().getRoomType().getId() == roomType.getId()}">selected</c:if>>${roomType.getName()}</option>
                </c:forEach>
            </select>
            <select name="id_room" id="id_room" <c:if test="${booking == null}">disabled </c:if> required>
                <c:if test="${booking != null}"><option value="${booking.getRoom().getId()}">${booking.getRoom().getNumber()}</option> </c:if>
            </select>
        </div>
        <label for="id_guest">Guest</label>
        <select name="id_guest" id="id_guest" required>
            <option disabled selected value></option>
            <c:forEach items="${guestList}" var="guest">
                <option value="${guest.getId()}" <c:if test="${guest.getId() == booking.getGuest().getId()}"> selected </c:if>>${guest.getName()}</option>
            </c:forEach>
        </select>
        <input type="text" value="${sessionStaff.getId()}" style="display: none" id="id_staff" name="id_staff">
        <span class="total">Total $<span id="result">${booking.getTotal()}</span></span>
        <input type="number" style="display: none" name="id_staff" id="id_staff" value="${sessionStaff.getId()}">
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
        let url = "/bookings/${booking.getId()}";

        fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'id_room': document.getElementById("id_room").value,
                'id_guest': document.getElementById("id_guest").value,
                'arrival': document.getElementById("arrival").value,
                'departure': document.getElementById("departure").value,
                'id_staff': ${sessionStaff.getId()},
                'total': totalCalc
            }),
        }).then(resp => {   window.location.href = url });
    }

    if(document.getElementById("form").method !== "post"){
        document.getElementById("form").addEventListener("submit", send);
    }

    let roomContent = document.getElementById("id_room").innerHTML;
    let roomTypeValue = document.getElementById("room_type").value;
    let roomTypeContent = document.getElementById("room_type").innerHTML;
    <c:if test="${booking != null}">
        let totalCalc = ${booking.getTotal()};
    </c:if>


    document.getElementById("arrival").addEventListener("change", prepareDepartureDate);
    document.getElementById("departure").addEventListener("focusout", validate);

    document.getElementById("departure").oninput = function () {
        if((document.getElementById("departure").value !== "") && (document.getElementById("arrival").value !== "")){
            reset(validate(), document.getElementById("room_type").value !== "");
        }else{
            reset(false);

        }
        if(document.getElementById("arrival").value !== ""){
            if(validate()){
                calcPrice();
            }
        }

    };
    document.getElementById("arrival").oninput = function () {
        if((document.getElementById("departure").value !== "") && (document.getElementById("arrival").value !== "")){
            reset(validate(), document.getElementById("room_type").value !== "");
        }else{
            reset(false);

        }
        if(document.getElementById("departure").value !== ""){
            if(validate()){
                calcPrice();
            }
        }

    };
    document.getElementById("room_type").onchange = function () {
        if(roomContent !== ""){
            calcPrice();
            if(document.getElementById("room_type").value === roomTypeValue){
                document.getElementById("id_room").innerHTML = roomContent;
            }else{
                document.getElementById("id_room").innerHTML = "";
            }
        }else{
            document.getElementById("id_room").innerHTML = "";
        }
        if(document.querySelector("#room_type").value !== ""){
            document.querySelector("#id_room").disabled = false;
            research_rooms();
        }else{
            document.querySelector("#id_room").disabled = true;
        }
    };

    function validate() {
        if(document.getElementById("arrival").value >= document.getElementById("departure").value){
            document.getElementById("arrival").setCustomValidity("Date start is bigger than date end.");
            return false;
        }else{
            document.getElementById("arrival").setCustomValidity("");
            return true;
        }
    }

    function reset(validator, rt){
        if(validator){
            if(!rt){
                document.getElementById("room_type").disabled = false;
                document.getElementById("room_type").innerHTML = roomTypeContent;
            }else{
                document.getElementById("id_room").innerHTML = '';
                research_rooms();
            }
            calcPrice();
        }else{
            document.getElementById("room_type").disabled = true;
            document.getElementById("room_type").innerHTML = '';
            document.getElementById("result").innerHTML = '';
            document.getElementById("id_room").innerHTML = '';
            document.getElementById("id_room").disabled = true;
        }

    }

    function research_rooms() {
        let url = "/api/room?arrival="+ document.getElementById("arrival").value + "&departure=" + document.getElementById("departure").value +"&room_type=" + document.getElementById("room_type").value;
        fetch(url, {
            method: 'GET',
        }).then(resp => resp.json())
            .then(data => {
                if(!data){
                    return;
                }
                for (i = 0; i< data.length; i++) {
                    let opt = document.createElement("option");
                    opt.value = data[i].id;
                    opt.innerText = data[i].number;
                    document.querySelector("#id_room").appendChild(opt);
                }
            });
    }

    function calcPrice(){
        if(document.getElementById("room_type").value !== ""){
            let url = "/api/roomtype?room_type=" + document.getElementById("room_type").value;
            fetch(url, {
                method: 'GET',
            }).then(resp => resp.json())
                .then(data => {
                    let dailyPrice = data.dailyPrice;
                    let total = dailyPrice * (document.getElementById("departure").valueAsNumber - document.getElementById("arrival").valueAsNumber) / (1000 * 3600 * 24);
                    totalCalc = total+0.00;
                    document.getElementById("result").innerHTML = total.toFixed(2);
                });
        }
    }

    function prepareDepartureDate(){
        let arrivalDate = new Date(document.getElementById("arrival").value);
        arrivalDate.setDate(arrivalDate.getDate() + 2);
        const months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
        if(arrivalDate.getDate()<10){
            document.getElementById("departure").min = arrivalDate.getFullYear()+"-"+months[arrivalDate.getMonth()]+"-0"+arrivalDate.getDate();
        }else{
            document.getElementById("departure").min = arrivalDate.getFullYear()+"-"+months[arrivalDate.getMonth()]+"-"+arrivalDate.getDate();
        }



    }

</script>
</html>

