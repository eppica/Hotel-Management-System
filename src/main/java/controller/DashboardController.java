package controller;

import model.Booking;
import model.Payment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "DashboardController", urlPatterns = {"/dashboard/*"})
public class DashboardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!Servlet.isLogged(req)){
            resp.sendRedirect("/auth/login");
        }else{
            List<Booking> arrivalList = Booking.findAllArrival();
            req.setAttribute("arrivalList", arrivalList);
            List<Booking> departureList = Booking.findAllDeparture();
            HashMap<Integer, Boolean> paid = new HashMap<Integer, Boolean>();
            if(departureList!=null) {
                for(Booking book : departureList){
                    paid.put(book.getId(), book.getTotal().equals(Payment.sumAll(book.getId())));
                }
            }
            req.setAttribute("departureList", departureList);
            req.setAttribute("paid", paid);
            req.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
