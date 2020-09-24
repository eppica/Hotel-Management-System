package controller;

import model.Room;
import model.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "APIController", urlPatterns = {"/api/*"})
public class APIController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = getOperation(req);
        if(operation == 1){
            String arrival = req.getParameter("arrival");
            String departure = req.getParameter("departure");
            String roomType = req.getParameter("room_type");

            StringBuilder json = null;
            if((arrival!= null) && (departure!= null) && (roomType!= null)){
                List<Room> roomList = Room.findAll("WHERE room_type_fk = " + roomType + " AND id NOT IN (SELECT room_fk FROM booking WHERE (arrival <= '"+ departure +"' AND departure >= '" + arrival +"') AND booking.id NOT IN (SELECT booking_fk FROM `check` WHERE  status = 0));");
                if(!roomList.isEmpty()){
                    json = new StringBuilder("[");
                    for (Room room : roomList) {
                        json.append(room.toJSON());
                        json.append(",");
                    }
                    json = new StringBuilder(json.substring(0, json.length() - 1));
                    json.append("]");
                }

            }
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        }else if(operation == 2){
            String idRoomType = req.getParameter("room_type");
            StringBuilder json = new StringBuilder();
            if((idRoomType!= null)){
                RoomType roomType = RoomType.find(Integer.valueOf(idRoomType));
                if(roomType != null){
                    json.append(roomType.toJSON());
                }

            }
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");

            resp.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        }else if(operation == 3){
            String arrival = req.getParameter("arrival");
            String departure = req.getParameter("departure");
            StringBuilder json = null;

            if((arrival!= null) && (departure!= null)){
                json = new StringBuilder("[");
                for (RoomType rT: RoomType.findAll() ){
                    Integer count = Room.findAll("WHERE room_type_fk = " + rT.getId() + " AND id NOT IN (SELECT room_fk FROM booking WHERE (arrival <= '"+ departure +"' AND departure >= '" + arrival +"') AND booking.id NOT IN (SELECT booking_fk FROM `check` WHERE  status = 0));").size();
                    json.append("{\"id\":\""+rT.getId()+"\"");
                    json.append(", \"count\":\""+ count + "\"},");
                }
                json = new StringBuilder(json.substring(0, json.length() - 1));
                json.append("]");
            }
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


    private Integer getOperation(HttpServletRequest request){
        String url = request.getPathInfo();
        if(url==null){
            url = "/";
        }
        Pattern pattern = Pattern.compile("(\\/room$)|(\\/roomtype)|(\\/count)");
        Matcher matcher = pattern.matcher(url);

        /*
            group 0 = full match
            1 = /api/room
            2 = /api/roomtype
            3 = /api/count
         */

        if(matcher.find()){
            if(matcher.group(1) != null){
                return 1;
            }else if (matcher.group(2) != null){
                return 2;
            }else if (matcher.group(3) != null){
                return 3;
            }
        }
        return -1;
    }
}
