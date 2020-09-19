<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <div class="logo" onclick="window.location.href='/dashboard';">
        <h1>HMS</h1>
    </div>
    <nav>

        <c:if test="${sessionStaff.getAccessLevel() == 'STAFF'}">
            <c:import url="/WEB-INF/header/staffOptions.jsp"/>
        </c:if>
        <c:if test="${sessionStaff.getAccessLevel() == 'OWNER'}">
            <c:import url="/WEB-INF/header/ownerOptions.jsp"/>
        </c:if>


    </nav>
    <div class="user">
        <h2>${sessionStaff.getName()}</h2>
        <a href="/auth/logout">Logout</a>
    </div>
</header>
