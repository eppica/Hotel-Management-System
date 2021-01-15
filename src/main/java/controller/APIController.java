package controller;

import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "APIController", urlPatterns = {"/api/*"})
public class APIController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = getOperation(req);
        if(Servlet.isLogged(req)){
            if(Servlet.isAllowed(req, AccessLevel.OWNER)){
                if (operation == 4) {
                    StringBuilder json = new StringBuilder("{");

                    LocalDate today = LocalDate.now();

                    BigDecimal yearRevenues = new BigDecimal(0);
                    yearRevenues = yearRevenues.add(Payment.sumAll(" WHERE year(payTime) = " + today.getYear()));

                    json.append("\"yearRevenues\": " + yearRevenues.toString() + ",");

                    BigDecimal monthRevenues = Payment.sumAll(" WHERE month(payTime) = " + today.getMonthValue());

                    json.append("\"monthRevenues\": " + monthRevenues + ",");

                    BigDecimal weekRevenues = Payment.sumAll(" WHERE date(payTime) BETWEEN  '" + today.plusDays(-7) + "' AND '" + today + "'");

                    json.append("\"weekRevenues\": " + weekRevenues + ",");

                    json.append("\"weekRevenuesDetailed\": {");
                    LocalDate week = LocalDate.now();
                    for (int i = 0; i < 7; i++) {

                        BigDecimal dayRevenues = Payment.sumAll(" WHERE date(payTime) = '" + week + "'");
                        json.append("\"" + week.getMonthValue() + "-" + week.getDayOfMonth() + "\": " + dayRevenues + ",");
                        week = week.plusDays(-1);

                    }
                    json = new StringBuilder(json.substring(0, json.length() - 1));
                    json.append("},");


                    List<Booking> arrivalList = Booking.findAllArrival();
                    List<Booking> departureList = Booking.findAllDeparture();

                    json.append("\"todayArrives\": " + ((arrivalList!=null)?arrivalList.size():"0") + ",");
                    json.append("\"todayDepartures\": " + ((departureList!=null)?departureList.size():"0") + ",");

                    List<Room> roomList = Room.findAll();

                    int occupiedRooms = 0;
                    int availableRooms = 0;
                    for(Room room:roomList){
                        if(room.getAvailability() == "Occupied"){
                            occupiedRooms++;
                        }else{
                            availableRooms++;
                        }
                    }

                    json.append("\"occupiedRooms\": " + occupiedRooms + ",");

                    json.append("\"availableRooms\": " + availableRooms + ",");

                    json.append("\"totalRooms\": " + (occupiedRooms + availableRooms) + ",");

                    json.append(("\"trendingRooms\": {"));
                    for (RoomType type : RoomType.findAll()) {
                        List<Booking> total = Booking.findAll("WHERE room IN (SELECT id FROM Room WHERE roomType = " + type.getId() + ")");
                        json.append("\"" + type.getName() + "\": " + ((total!=null)?total.size():"0") + ",");

                    }
                    if(RoomType.findAll()==null || RoomType.findAll().size()==0){
                        json.append("\"No rooms registered\":\" \"  ");
                    }
                    json = new StringBuilder(json.substring(0, json.length() - 1));
                    json.append("}}");

                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json;charset=UTF-8");
                    out.print(json);
                    out.flush();


                }
            }
            if (operation == 1) {
                String arrival = req.getParameter("arrival");
                String departure = req.getParameter("departure");
                String roomType = req.getParameter("room_type");

                StringBuilder json = null;
                if ((arrival != null) && (departure != null) && (roomType != null)) {
                    List<Room> roomList = Room.findAll("WHERE roomType = " + roomType + " AND id NOT IN (SELECT room FROM Booking WHERE (arrival <= '" + departure + "' AND departure >= '" + arrival + "') AND id NOT IN (SELECT booking FROM Checks WHERE  status = 0))");
                    if(roomList!=null) {
                        if (!roomList.isEmpty()) {
                            json = new StringBuilder("[");
                            for (Room room : roomList) {
                                json.append(room.toJSON());
                                json.append(",");
                            }
                            json = new StringBuilder(json.substring(0, json.length() - 1));
                            json.append("]");
                        }
                    }

                }
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                out.print(json);
                out.flush();
            } else if (operation == 2) {
                String idRoomType = req.getParameter("room_type");
                StringBuilder json = new StringBuilder();
                if ((idRoomType != null)) {
                    RoomType roomType = RoomType.find(Integer.valueOf(idRoomType));
                    if (roomType != null) {
                        json.append(roomType.toJSON());
                    }

                }
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");

                out.print(json);
                out.flush();
            } else if (operation == 3) {
                String arrival = req.getParameter("arrival");
                String departure = req.getParameter("departure");
                StringBuilder json = null;

                if ((arrival != null) && (departure != null)) {
                    json = new StringBuilder("[");
                    for (RoomType rT : RoomType.findAll()) {
                        Integer count = Room.findAll("WHERE roomType = " + rT.getId() + " AND id NOT IN (SELECT room FROM Booking WHERE (arrival <= '" + departure + "' AND departure >= '" + arrival + "') AND id NOT IN (SELECT booking FROM Checks  WHERE status = 0))").size();
                        json.append("{\"id\":\"" + rT.getId() + "\"");
                        json.append(", \"count\":\"" + count + "\"},");
                    }
                    json = new StringBuilder(json.substring(0, json.length() - 1));
                    json.append("]");
                }
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                out.print(json);
                out.flush();

            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


    private Integer getOperation(HttpServletRequest request) {
        String url = request.getPathInfo();
        if (url == null) {
            url = "/";
        }
        Pattern pattern = Pattern.compile("(\\/room$)|(\\/roomtype)|(\\/count)|(\\/ownerDashboard)");
        Matcher matcher = pattern.matcher(url);

        /*
            group 0 = full match
            1 = /api/room
            2 = /api/roomtype
            3 = /api/count
            4 = /api/ownerDashboard
         */

        if (matcher.find()) {
            if (matcher.group(1) != null) {
                return 1;
            } else if (matcher.group(2) != null) {
                return 2;
            } else if (matcher.group(3) != null) {
                return 3;
            } else if (matcher.group(4) != null) {
                return 4;
            }
        }
        return -1;
    }
}
