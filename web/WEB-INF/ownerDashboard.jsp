<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="/js/Chart.min.js"></script>
<div class="content">

    <h1 id="today"></h1>

    <div id="revenues">
        <h2>Revenues</h2>
        <div class="property" style="grid-area: 2/1/3/2;">
            <span class="label">Year</span>
            <span class="data">$ <span id="yearRevenues"></span></span>
        </div>

        <div class="property" style="grid-area: 2/2/3/3;">
            <span class="label">Week</span>
            <span class="data">$ <span id="weekRevenues"></span></span>
        </div>
        <div class="property" style="grid-area: 3/1/4/2;">
            <span class="label">Month</span>
            <span class="data">$ <span id="monthRevenues"></span></span>
        </div>
        <div class="property" style="grid-area: 3/2/4/3;">
            <span class="label">Today</span>
            <span class="data">$ <span id="dayRevenues"></span></span>
        </div>
        <div style="grid-area: 1/3/4/5;">
            <canvas id="revenuesChart"></canvas>
        </div>
    </div>
    <div id="checks">
        <h2>Today</h2>
        <div class="property" style="grid-area: 2/1/3/2;">
            <span class="label">Expected Arrives</span>
            <span class="data"><span id="expectedArrives"></span></span>
        </div>
        <div class="property" style="grid-area: 2/2/3/3;">
            <span class="label">Expected Departures</span>
            <span class="data"><span id="expectedDepartures"></span></span>
        </div>

    </div>
    <div id="rooms">
        <h2>Hotel Current Situation</h2>
        <div class="property" style="grid-area: 2/1/3/2;">
            <span class="label">Occupied</span>
            <span class="data"><span id="occupiedRooms"></span></span>
        </div>
        <div class="property" style="grid-area: 2/2/3/3;">
            <span class="label">Available</span>
            <span class="data"><span id="availableRooms"></span></span>
        </div>
        <div class="property" style="grid-area: 2/3/3/4;">
            <span class="label">Total</span>
            <span class="data"><span id="totalRooms"></span></span>
        </div>
        <div style="grid-area: 1/4/4/5;">
            <canvas id="occupationRooms"></canvas>
        </div>
    </div>
    <div id="trending">
        <div style="grid-area: 1/2/1/2;">
            <canvas id="trendingRoomTypes"></canvas>
        </div>
    </div>


</div>

<script>
    let today = document.getElementById("today");

    const monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];

    let now = new Date();
    let dateNow = monthNames[now.getMonth()] + " " + now.getDate() + ", " + now.getFullYear();
    today.innerText = dateNow;

    let hoursToMidnight = 24 - now.getHours();
    let minutesToNextHour = 60 - now.getMinutes();
    let secondsToNextMinute = 60 - now.getSeconds();

    let minutesToMidnight = (hoursToMidnight * 60) + minutesToNextHour;

    let secondsToMidnight = (minutesToMidnight * 60) + secondsToNextMinute;

    let millisecondsToMidnight = secondsToMidnight * 1000;

    setTimeout(function () {
        window.location.reload();
    }, millisecondsToMidnight)


    let url = "/api/ownerDashboard";
    fetch(url, {method: 'GET'})
        .then(response => response.json())
        .then(data => {

            let weekRevenuesDetailed = Object.values(data.weekRevenuesDetailed);

            document.getElementById("yearRevenues").innerHTML = data.yearRevenues;
            document.getElementById("monthRevenues").innerHTML = data.monthRevenues;
            document.getElementById("weekRevenues").innerHTML = data.weekRevenues;
            document.getElementById("dayRevenues").innerHTML = weekRevenuesDetailed[0];

            document.getElementById("expectedArrives").innerHTML = data.todayArrives;
            document.getElementById("expectedDepartures").innerHTML = data.todayDepartures;

            document.getElementById("occupiedRooms").innerHTML = data.occupiedRooms;
            document.getElementById("availableRooms").innerHTML = data.availableRooms;
            document.getElementById("totalRooms").innerHTML = data.totalRooms;

            const colors = [ 'rgb(255, 159, 64)', 'rgb(75, 192, 192)', 'rgb(54, 162, 235)', 'rgb(255, 205, 86)', 'rgb(153, 102, 255)', 'rgb(255, 99, 132)','rgb(201, 203, 207)'];

            Chart.defaults.global.defaultFontColor = "#000";

            const configOccupiedRooms = {
                type: 'doughnut',
                data: {
                    datasets: [{
                        data: [
                            data.availableRooms,
                            data.occupiedRooms
                        ],
                        backgroundColor: [
                            colors[0],
                            colors[1]
                        ],
                        label: 'Dataset 1'
                    }],
                    labels: [
                        'Available',
                        'Occupied'
                    ]
                },
                options: {
                    responsive: true,
                    aspectRatio: 1.5,
                    legend: {
                        position: 'bottom',
                    },
                    title: {
                        display: true,
                        text: 'Capacity',
                        fontSize: '16'
                    },
                    animation: {
                        animateScale: true,
                        animateRotate: true
                    }
                }
            };

            let trendindRoomsQuantity = Object.values(data.trendingRooms);
            let trendindRoomsName = Object.keys(data.trendingRooms);
            let colorsTrendingRooms = colors.flat(data.trendingRooms.length)

            const configTrendingRoomTypes = {
                type: 'doughnut',
                data: {
                    datasets: [{
                        data: trendindRoomsQuantity,
                        backgroundColor: colorsTrendingRooms,
                        label: 'Dataset 1'
                    }],
                    labels: trendindRoomsName
                },
                options: {
                    responsive: true,
                    aspectRatio: 1.5,
                    legend: {
                        position: 'bottom',
                    },
                    title: {
                        display: true,
                        text: 'Most Popular Room Types',
                        fontSize: 19,
                        fontStyle: 'normal',
                        fontFamily: 'Arial, Helvetica, sans-serif'
                    },
                    animation: {
                        animateScale: true,
                        animateRotate: true
                    }
                }
            };

            let labelsRevenue = Object.keys(data.weekRevenuesDetailed);
            labelsRevenue.reverse();
            let valuesRevenues = Object.values(data.weekRevenuesDetailed);
            valuesRevenues.reverse();

            const configRevenues = {
                type: 'bar',
                data: {
                    labels: labelsRevenue,
                    datasets: [{
                        label: 'Dataset 1',
                        backgroundColor: 'rgb(54, 162, 235)',
                        borderColor: 'rgb(54, 162, 235)',
                        borderWidth: 1,
                        data: valuesRevenues
                    }]
                },
                options: {
                    responsive: true,
                    legend: {
                        display: false,
                        position: 'bottom',
                    },
                    title: {
                        display: true,
                        text: 'Revenues per day',
                        fontSize: '16'
                    }
                }
            };


            let ctxRevenues = document.getElementById('revenuesChart').getContext('2d');
            window.RevenuesChart = new Chart(ctxRevenues, configRevenues);

            let ctxOccupiedRooms = document.getElementById('occupationRooms').getContext('2d');
            window.occupiedRoomsChart = new Chart(ctxOccupiedRooms, configOccupiedRooms);

            let ctxTrendingRoomTypes = document.getElementById('trendingRoomTypes').getContext('2d');
            window.occupiedRoomTypesChart = new Chart(ctxTrendingRoomTypes, configTrendingRoomTypes);


        });


</script>