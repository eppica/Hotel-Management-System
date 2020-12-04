<%@ page import="java.time.LocalDateTime" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=0">
    <title>Dashboard</title>

    <c:if test="${sessionStaff.getAccessLevel() == 'STAFF'}">
        <c:set var="css" value="/css/dash.css" />
    </c:if>
    <c:if test="${sessionStaff.getAccessLevel() == 'OWNER'}">
        <c:set var="css" value="/css/ownerDash.css" />
    </c:if>


    <link rel="stylesheet" type="text/css" href="${css}">
</head>
<body>

<c:import url="/WEB-INF/header/main.jsp"/>

<c:if test="${sessionStaff.getAccessLevel() == 'STAFF'}">
    <c:import url="/WEB-INF/staffDashboard.jsp"/>
</c:if>
<c:if test="${sessionStaff.getAccessLevel() == 'OWNER'}">
    <c:import url="/WEB-INF/ownerDashboard.jsp"/>
</c:if>

</body>
</html>
