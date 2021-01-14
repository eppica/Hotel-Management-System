<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/one.css">
    <title>Booking</title>
    <meta name="viewport" content="width=device-width, user-scalable=0">
</head>
<c:if test="${allowed == true}">
    <style>
        .payment tbody tr:hover, td:hover{
            background-color: #f5f5f5;
            cursor: pointer;
        }

    </style>
</c:if>

<c:if test="${paymentList.size() == 0}">
    <style>
        .payment tbody tr:hover, td:hover{
            background-color: white;
            cursor: default;
        }

    </style>
</c:if>
<c:if test="${allowed == false}">
    <style>
        .edit {
            justify-content: flex-end;
        }
    </style>
</c:if>
<body onload="action('${booking.getStatus().toLowerCase()}')">
<c:import url="/WEB-INF/header/main.jsp"/>
<div class="content">
    <div class="model">
        <h1>Booking</h1>
        <h3>${booking.getStatus()}</h3>
    </div>

    <div class="edit">
<c:if test="${allowed == true}">
        <button class="delete" onclick="openModalDelete(${booking.getId()})">Delete</button>
</c:if>
        <button onclick="window.location.href='/bookings/${booking.getId()}/edit';">Edit</button>
    </div>
    <div class="about">
        <h2>About</h2>
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
        <div class="property link" onclick="window.location.href='/guests/${booking.getGuest().getId()}';">
            <span class="label">Guest</span>
            <span class="data">${booking.getGuest().getName()}</span>
        </div>
        <div class="property link" onclick="window.location.href='/rooms/${booking.getRoom().getId()}';">
            <span class="label">Room Number</span>
            <span class="data">${booking.getRoom().getNumber()}</span>
        </div>
        <div class="property">
            <span class="label">Total</span>
            <span class="data">$${booking.getTotal()}</span>
        </div>
    </div>

    <div class="payment">
        <div class="payment-info">
            <h2>Payments</h2>
            <div class="over">
                <table id="tablePayment">
                    <thead>
                        <tr><th>Paid out</th><th>Method</th><th>Timestamp</th><th class="delHide"></th></tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${paymentList}" var="payment">
                        <tr>
                            <td <c:if test="${allowed == true}">onclick="openModalPayment(${payment.getValue()}, '${payment.getPaymentMethod()}', ${booking.getTotal() - paid}, ${payment.getId()},'${payment.getStaff().getName()}')" </c:if>>$${payment.getValue()}</td>
                            <td <c:if test="${allowed == true}">onclick="openModalPayment(${payment.getValue()}, '${payment.getPaymentMethod()}', ${booking.getTotal() - paid}, ${payment.getId()},'${payment.getStaff().getName()}')" </c:if>>${payment.getPaymentMethod()}</td>
                            <td <c:if test="${allowed == true}">onclick="openModalPayment(${payment.getValue()}, '${payment.getPaymentMethod()}', ${booking.getTotal() - paid}, ${payment.getId()},'${payment.getStaff().getName()}')" </c:if>>${payment.getPayTime()}</td>
                            <c:if test="${allowed == true}">
                                <td class="delPayment delHide" onclick="openModalDeletePayment(${payment.getId()}, ${payment.getValue()})">Delete</td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    <c:if test="${paymentList.size() == 0}">
                        <tr><td>-</td><td>-</td><td>-</td></tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
            <div class="payment-result">
                <div class="property">
                    <span class="label">Paid out</span>
                    <span class="data">$ ${paid}</span>
                </div>
                <div class="property">
                    <span class="label">Remaining</span>
                    <span class="data" id="transformButton">$ ${booking.getTotal() - paid}</span>
                </div>
                <div class="property">
                    <button onclick="openModalPayment()" class="pay" id="p-button">Pay</button>
                </div>
            </div>
        </div>
    </div>



    <div class="checkin" id="div-checkin">
        <div class="checkin-info">
            <h2>Checkin</h2>
            <div class="property">
                <span class="label">Timestamp</span>
                <span class="data">${checkin.getCheckTime()}</span>
            </div>
        </div>
        <button id="do-checkin" onclick="openModalCheck(true, ${booking.getId()}, ${booking.getRoom().getNumber()})">Checkin</button>
        <button class="delete-check" onclick="openModalDeleteCheck(true, 10, 103)" style="display: none;">Delete</button>
    </div>


    <div class="checkout" id="div-checkout">
        <div class="checkout-info">
            <h2>Checkout</h2>
            <div class="property">
                <span class="label">Timestamp</span>
                <span class="data">${checkout.getCheckTime()}</span>
            </div>
        </div>
        <button id="do-checkout" onclick="openModalCheck(false, ${booking.getId()}, ${booking.getRoom().getNumber()})">Checkout</button>
        <button class="delete-check" onclick="openModalDeleteCheck(false, 10, 103)" style="display: none;">Delete</button>
    </div>
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
            <button onclick="link(${booking.getId()})" class="cancel"> Delete </button>
        </div>
    </div>
</div>

<div class="modal delete" id="modal-delete-payment">
    <div class="modal-content">
        <div class="modal-header">
            <h1>Delete</h1>
        </div>
        <div class="modal-body" id="pay-sure">
        </div>
        <div class="modal-footer">
            <button onclick="cancel()" type="button">Cancel</button>
            <button onclick="linkPayment()" class="cancel"> Delete </button>
        </div>
    </div>
</div>

<div class="modal delete" id="modal-delete-check">
    <div class="modal-content">
        <div class="modal-header">
            <h1>Delete</h1>
        </div>
        <div class="modal-body" id="sure-check">
        </div>
        <div class="modal-footer">
            <button onclick="cancel()" type="button">Cancel</button>
            <button onclick="linkCheck()" class="cancel"> Delete </button>
        </div>
    </div>
</div>


<div class="modal" id="modal-check">
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

<div class="modal" id="modal-payment">
    <div class="modal-content">
        <div class="modal-header">
            <h1>Payment</h1>
        </div>
        <div class="modal-body">
            <form action="/payments" id="pay-form" method="POST">
                <label for="value">Value</label>
                <input type="number" name="value" id="value" class="moneyInput" min="0" step="0.01"
                       autocomplete="off">
                <label for="payment_method">Method</label>
                <select name="payment_method" id="payment_method">
                    <option value="CASH">CASH</option>
                    <option value="CARD">CARD</option>
                </select>
                <label for="id_staff_payment" style="display: none">Staff</label>
                <input type="text" style="display: none" name="id_staff_payment" id="id_staff_payment"
                       value="${sessionStaff.getName()}" readonly>
                <input type="number" style="display: none" name="id_payment" id="id_payment" readonly>
                <input type="text" name="id_pay_booking" id="id_pay_booking" style="display: none" value="${booking.getId()}">
                <input type="datetime-local" name="pay_time" id="pay_time" autocomplete="off" style="display: none">
                <div class="modal-footer">
                    <button onclick="cancel()" type="button">Cancel</button>
                    <input type="submit" value="Receive" id="pay-button">
                </div>
            </form>
        </div>
    </div>
</div>

</body>
<script>

    function deleteCheckButton(t, test, info){
        if(test == true){
            document.querySelector("#" + t + " .delete-check").style.display = "block";
            document.querySelector("." + info).style.display = "none";
        }else{
            document.querySelector("#" + t + " .delete-check").style.display = "none";
            document.querySelector("." + info).style.display = "grid";
        }

    }

    function send(e){

        e.preventDefault();
        let url = "/payments/" + document.querySelector("#pay-form #id_payment").value;

        fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'value': document.getElementById("value").value,
                'payment_method': document.getElementById("payment_method").value,
                'id_booking': document.getElementById("id_pay_booking").value,
                'id_staff': ${sessionStaff.getId()},
                'pay_time':document.getElementById("pay_time").value
            }),
        }).then(resp => { location.reload() });
    }

    function sendPost(e){

        e.preventDefault();
        let url = "/payments";

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'value': document.getElementById("value").value,
                'payment_method': document.getElementById("payment_method").value,
                'id_booking': document.getElementById("id_pay_booking").value,
                'id_staff': ${sessionStaff.getId()},
                'pay_time':document.getElementById("pay_time").value
            }),
        }).then(resp => { location.reload() });
    }

    function changeMethod(){
        if((document.getElementById("pay-form").method == "POST") || (document.getElementById("pay-form").method == "post")){
            document.getElementById("pay-form").addEventListener("submit", sendPost);

        }else{
            document.getElementById("pay-form").addEventListener("submit", send);
        }
    }

    let modalDelete = document.getElementById("modal-delete");
    let modalCheck = document.getElementById("modal-check");
    let modalPayment = document.getElementById("modal-payment");
    let modalDeletePayment = document.getElementById("modal-delete-payment");
    let modalDeleteCheck = document.getElementById("modal-delete-check");
    let title = document.getElementById("title");
    let currentPayId;
    let paid = ${booking.getTotal() - paid};
    let delcheck;

    <c:if test="${checkin != null}">
        delcheck = ${checkin.getId()};
    </c:if>

    <c:if test="${checkout != null}">
        delcheck = ${checkout.getId()};
    </c:if>

    if(paid == 0){
        document.getElementById("p-button").style.display="none";
    }else{
        document.getElementById("p-button").style.display="block";
    }

    function openModalDelete(booking) {
        modalDelete.style.display = "flex";
        document.getElementById("sure").innerHTML = "Delete booking " + booking + "?";
    }

    function openModalDeleteCheck(test, booking, room){
        modalDeleteCheck.style.display = "flex";
        if(test === true){
            document.getElementById("sure-check").innerHTML = "Delete this booking checkin?";
        }else{
            document.getElementById("sure-check").innerHTML = "Delete this booking checkout";
        }

    }

    function linkCheck(){
        let url = "/checks/"+ delcheck;
        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
        }).then(resp => {   location.reload()  });
    }


    function cancel(){
        modalDelete.style.display = "none";
        modalCheck.style.display = "none";
        modalPayment.style.display = "none";
        modalDeletePayment.style.display = "none";
        modalDeleteCheck.style.display = "none";
    }

    function link(id) {
        let url = "/bookings/"+ id;
        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
        }).then(resp => {   window.location.href = "/bookings" });
    }

    function openModalCheck(test, booking, room) {
        if(test){
            title.innerHTML = "Checkin";
            document.getElementById("status").value = 1;
        }else{
           title.innerHTML = "Checkout";
            document.getElementById("status").value = 0;

        }
        modalCheck.style.display = "flex";
        document.getElementById("id_booking").value = booking;
        document.getElementById("room_number").value = room;
        let now = new Date();
        now.setHours(now.getHours() - 3); //because of the timezone
        document.getElementById("check").value = now.toISOString().substring(0, 16);
    }

    window.onclick = function(event) {
        if (event.target === modalCheck || event.target === modalDelete || event.target === modalPayment || event.target === modalDeleteCheck || event.target === modalDeletePayment) {
            modalCheck.style.display = "none";
            modalPayment.style.display = "none";
            modalDelete.style.display = "none";
            modalDeleteCheck.style.display = "none";
            modalDeletePayment.style.display = "none";
        }
    };

    function openModalPayment(value, method, remaining, id, staff) {
        modalPayment.style.display = "flex";
        if((value !== null) && (method !== null) && (remaining != null) && (id != null)){
            document.getElementById("value").value = value;
            document.getElementById("value").max = value + remaining;
            for(let opt of document.getElementById("payment_method").options){
                if(opt.value === method){
                    opt.selected = true;
                }
            }
            document.querySelector("#pay-form #id_payment").value = id;
            document.querySelector("#modal-payment h1").innerHTML = "Edit Payment";
            document.getElementById("pay-form").method = "";
            document.getElementById("pay-form").action = "";
            changeMethod();
            document.getElementById("pay-button").value = "Edit";
            document.getElementById("id_staff_payment").style.display = "block";
            document.querySelector("label[for='id_staff_payment']").style.display = "block";
            document.getElementById("id_staff_payment").value = staff;
        }else{
            document.querySelector("#pay-form #id_payment").value = 0;
            document.getElementById("value").max =${booking.getTotal() - paid};
            document.getElementById("value").value =${booking.getTotal() - paid};
            document.getElementById("pay-form").method = "POST";
            document.getElementById("pay-button").value = "Receive";
            document.querySelector("#modal-payment h1").innerHTML = "Payment";
            document.getElementById("pay-form").action = "/payments";
            document.getElementById("id_staff_payment").style.display = "none";
            document.querySelector("label[for='id_staff_payment']").style.display = "none";
            changeMethod();
        }
        let now = new Date();
        now.setHours(now.getHours() - 3); //because of the timezone
        document.getElementById("pay_time").value = now.toISOString().substring(0, 16);
    }

    function openModalDeletePayment(id, value) {
        modalDeletePayment.style.display = "flex";
        document.getElementById("pay-sure").innerHTML = "Delete payment $" + value + "?";
        currentPayId = id;
    }

    function linkPayment() {
        let url = "/payments/"+ currentPayId;
        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
        }).then(resp => { location.reload() });
    }


    function action(operation){
        let checkin_info = document.querySelector(".checkin-info");
        let do_checkin = document.querySelector("#do-checkin");
        let checkout_info = document.querySelector(".checkout-info");
        let do_checkout = document.querySelector("#do-checkout");

        beautifulTimestamp();


        if(operation === "booked"){
            checkin_info.style.display = "none";
            do_checkin.style.display = "block";
            do_checkout.style.display = "none";
            checkout_info.style.display = "none";
        }else if(operation === "arrived"){
            checkin_info.style.display = "grid";
            do_checkin.style.display = "none";
            <c:if test="${allowed == true}">
                document.getElementById("div-checkin").addEventListener("mouseover", () => { deleteCheckButton("div-checkin", true, "checkin-info"); });
                document.getElementById("div-checkin").addEventListener("mouseout", () => { deleteCheckButton("div-checkin", false, "checkin-info"); });
            </c:if>
            if(paid == 0){
                do_checkout.style.display = "block";
                checkout_info.style.display = "none";
            }else{
                do_checkout.style.display = "none";
                checkout_info.style.display = "none";
            }
        }else if(operation === "departed"){
            checkin_info.style.display = "grid";
            do_checkin.style.display = "none";
            do_checkout.style.display = "none";
            checkout_info.style.display = "grid";
            <c:if test="${allowed == true}">
                document.getElementById("div-checkout").addEventListener("mouseover", () => { deleteCheckButton("div-checkout", true, "checkout-info"); });
                document.getElementById("div-checkout").addEventListener("mouseout", () => { deleteCheckButton("div-checkout", false, "checkout-info"); });
            </c:if>
            for(let x of document.querySelector("#tablePayment").rows){
                x.deleteCell(3);
            }

        }
        beautifulTimestamp();
    }

    function beautifulTimestamp() {

        const regex = new RegExp('[0-9]{1,4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{1,2}:[0-9]{1,2}', 'g');

        let datas = Array.from(document.getElementsByClassName("data"));
        let tds = Array.from(document.getElementsByTagName("td"));
        let classes = datas.concat(tds);

        for(let classe of classes) {
            if (regex.test(classe.innerText)) {
                classe.innerText = classe.innerText.replace("T", " ");
            }
        }
    }

    beautifulTimestamp();

</script>
</html>
