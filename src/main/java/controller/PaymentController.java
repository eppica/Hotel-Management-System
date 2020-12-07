package controller;

import model.Payment;

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

@WebServlet(name = "PaymentController", urlPatterns = {"/payments/*"})
public class PaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if(operation == 1){
            BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String data = br.readLine();
            Payment payment = new Payment((URLDecoder.decode(data,  StandardCharsets.UTF_8.toString()).split("&")));
            payment.save();
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
            Payment payment = new Payment((URLDecoder.decode(data,  StandardCharsets.UTF_8.toString()).split("&")));
            payment.setId(Servlet.getId(req));
            payment.update();
        }else{
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if (operation == 2) {
            Payment.delete(Servlet.getId(req));
        }else {
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }
}
