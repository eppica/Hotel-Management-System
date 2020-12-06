package controller;

import model.AccessLevel;
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

@WebServlet(name = "StaffController", urlPatterns = {"/staff/*"})
public class StaffController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!Servlet.isLogged(req)){
            resp.sendRedirect("/auth/login");
        }else{
            Integer operation = Servlet.getOperation(req);
            if(Servlet.isAllowed(req, AccessLevel.OWNER)){
                if (operation == 1) {
                    List<Staff> staffList = Staff.findAll();
                    req.setAttribute("staffList", staffList);
                    req.getRequestDispatcher("/WEB-INF/staff/findAll.jsp").forward(req, resp);
                }else if (operation == 2){
                    Staff staff = Staff.find(Servlet.getId(req));
                    req.setAttribute("staff", staff);
                    req.setAttribute("sessionStaff",(Staff) req.getSession().getAttribute("sessionStaff"));
                    req.getRequestDispatcher("/WEB-INF/staff/find.jsp").forward(req, resp);
                }else if (operation == 3){
                    Staff staff = Staff.find(Servlet.getId(req));
                    req.setAttribute("staff", staff);
                    req.setAttribute("accessLevelList", AccessLevel.values());
                    req.getRequestDispatcher("/WEB-INF/staff/form.jsp").forward(req, resp);
                }else if(operation == 4){
                    req.setAttribute("accessLevelList", AccessLevel.values());
                    req.getRequestDispatcher("/WEB-INF/staff/form.jsp").forward(req, resp);
                }else{
                    req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
                }
            }else {
                req.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if(operation == 1){
            Staff staff = new Staff(req);
            staff.save();
            resp.sendRedirect("/staff/" + staff.getId());
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
            Staff staff = new Staff(URLDecoder.decode(data,  StandardCharsets.UTF_8.toString()).split("&"));
            staff.setId(Servlet.getId(req));
            staff.update();
            Staff staffSession = (Staff) req.getSession().getAttribute("sessionStaff");
            if(staff.getId().equals(staffSession.getId())){
                req.getSession().setAttribute("sessionStaff", staff);
            }
        }else {
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = Servlet.getOperation(req);
        if (operation == 2) {
            Staff.delete(Servlet.getId(req));
            Staff staffSession = (Staff) req.getSession().getAttribute("sessionStaff");
            if(Servlet.getId(req).equals(staffSession.getId())){
                req.getRequestDispatcher("/auth/logout").forward(req, resp);
            }
        }else {
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }
}
