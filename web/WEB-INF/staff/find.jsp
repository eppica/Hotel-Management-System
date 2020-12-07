<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/one.css">
    <title>Staff</title>
    <meta name="viewport" content="width=device-width, user-scalable=0">
</head>
<body>
<c:import url="/WEB-INF/header/main.jsp"/>

<c:if test="${sessionStaff.getId() == staff.getId()}">
    <style>
        .edit {
            justify-content: flex-end;
        }
    </style>
</c:if>

<div class="content">
    <div class="model">
        <h1>${staff.getName()}</h1>
        <h3>${staff.getAccessLevel()}</h3>
    </div>
    <div class="edit">
        <c:if test="${sessionStaff.getId() != staff.getId()}">
            <button class="delete" onclick="openModal('${staff.getName()}')">Delete</button>
        </c:if>
        <button onclick="window.location.href='/staff/${staff.getId()}/edit';">Edit</button>
    </div>

    <div class="about">
        <h2>About</h2>
        <div class="property">
            <span class="label">ID</span>
            <span class="data">${staff.getId()}</span>
        </div>
        <div class="property">
            <span class="label">Name</span>
            <span class="data">${staff.getName()}</span>
        </div>
        <div class="property">
            <span class="label">Access Level</span>
            <span class="data">${staff.getAccessLevel()}</span>
        </div>
        <div class="property">
            <span class="label">Login</span>
            <span class="data">${staff.getLogin()}</span>
        </div>

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
            <button onclick="link(${staff.getId()})" class="cancel"> Delete </button>
        </div>
    </div>
</div>
</body>
<script>
    let modal = document.getElementById("modal-delete");
    function openModal(staff) {
        modal.style.display = "flex";
        document.getElementById("sure").innerHTML = "Delete staff " + staff + "?";
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
        let url = "/staff/"+ id;
        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
        }).then(resp => {   window.location.href = "/staff" });
    }
</script>
</html>
