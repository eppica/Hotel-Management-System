<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/find.css">
    <title>Check Guest</title>
    <meta name="viewport" content="width=device-width, user-scalable=0">
</head>
<body>
<h1>Check Guest</h1>
<div class="content">
    <div class="over">
        <table>
            <tr><th>Guest</th><th>Time</th><th>Status</th><th colspan="2">Action</th></tr>
            <c:forEach items="${checkList}" var="check">
                <tr><td>${check.getBooking().getGuest().getName()}</td><td>${check.getCheckTime()}</td><c:choose><c:when test="${check.getStatus()==true}"><td class="checkin">Check-in</td></c:when><c:otherwise><td class="checkout">Check-out</td></c:otherwise></c:choose><td class="link"><a href="/checks/${check.getId()}/edit">Update</a></td><td class="link"><a onclick="link(${check.getId()})">Delete</a></td></tr>
            </c:forEach>

        </table>
    </div>


    </table>
    <button><a href="/checks/new">NEW</a></button>
</div>

</body>

<script>
    function link(id) {
        console.log(id);
        let url = "/checks/"+ id;

        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
        }).then(resp => {   window.location.href = "/checks" });
    }
</script>
</html>

