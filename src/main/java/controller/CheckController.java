package controller;

import model.Booking;
import model.Checks;
import model.Staff;

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
import java.util.List;

@WebServlet(name = "CheckController", urlPatterns = {"/checks/*"})
public class CheckController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if (operation == 1) {
            List<Checks> checkList = Checks.findAll();
            req.setAttribute("checkList", checkList);
            req.getRequestDispatcher("/WEB-INF/check/findAll.jsp").forward(req, resp);
        }else if(operation == 2){
            Checks check = Checks.find(Servlet.getId(req));
            req.setAttribute("check", check);
            req.getRequestDispatcher("/WEB-INF/check/find.jsp").forward(req, resp);
        } else if (operation == 3) {
            Checks check = Checks.find(Servlet.getId(req));
            req.setAttribute("check", check);
            req.setAttribute("staffList", Staff.findAll());
            req.setAttribute("bookingList", Booking.findAll());
            req.getRequestDispatcher("/WEB-INF/check/form.jsp").forward(req, resp);
        } else if (operation == 4) {
            req.setAttribute("staffList", Staff.findAll());
            req.setAttribute("bookingList", Booking.findAll());
            req.getRequestDispatcher("/WEB-INF/check/form.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if (operation == 1) {
            Checks check = new Checks(req);
            check.save();
            resp.sendRedirect("/bookings/"+check.getBooking().getId());
        }else{
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if (operation == 2) {
            BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String data = br.readLine();
            Checks check = new Checks(URLDecoder.decode(data,  StandardCharsets.UTF_8.toString()).split("&"));
            check.setId(Servlet.getId(req));
            check.update();
        }else{
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if (operation == 2) {
            Checks.delete(Servlet.getId(req));
        }else{
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }
}
