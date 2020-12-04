package controller;

import model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "RoomController", urlPatterns = {"/rooms/*"})
public class RoomController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!Servlet.isLogged(req)){
            resp.sendRedirect("/auth/login");
        }else{
            Integer operation = Servlet.getOperation(req);
            if(operation == 1){
                List<Room> roomList = Room.findAll();
                req.setAttribute("roomList", roomList);
                req.setAttribute("allowed", Servlet.isAllowed(req, AccessLevel.OWNER));
                req.getRequestDispatcher("/WEB-INF/room/findAll.jsp").forward(req, resp);
            }else if(operation == 2){
                Room room = Room.find(Servlet.getId(req));
                req.setAttribute("room", room);
                Booking booking = Booking.findBookedRoom(room.getId());
                req.setAttribute("booking", booking);
                req.setAttribute("allowed", Servlet.isAllowed(req, AccessLevel.OWNER));
                req.getRequestDispatcher("/WEB-INF/room/find.jsp").forward(req, resp);
            }else if(operation == 3){
                if(Servlet.isAllowed(req, AccessLevel.OWNER)){
                    Room room = Room.find(Servlet.getId(req));
                    req.setAttribute("room", room);
                    req.setAttribute("roomTypeList", RoomType.findAll());
                    req.getRequestDispatcher("/WEB-INF/room/form.jsp").forward(req, resp);
                }else{
                    req.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
                }
            }else if(operation == 4){
                if(Servlet.isAllowed(req, AccessLevel.OWNER)){
                    req.setAttribute("roomTypeList", RoomType.findAll());
                    req.getRequestDispatcher("/WEB-INF/room/form.jsp").forward(req, resp);
                }else{
                    req.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
                }
            }else{
                req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if(operation == 1){
            Room room = new Room(req);
            room = room.save();
            resp.sendRedirect("/rooms/"+room.getId());
        }else {
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if(operation == 2){
            BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String data = br.readLine();
            Room room = new Room(URLDecoder.decode(data,  StandardCharsets.UTF_8.toString()).split("&"));
            room.setId(Servlet.getId(req));
            room.update();
        }else {
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if(operation == 2){
            Room.delete(Servlet.getId(req));
        }else{
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }
}
