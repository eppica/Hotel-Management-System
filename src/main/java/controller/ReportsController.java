package controller;

import dao.DB;
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ReportsController", urlPatterns = {"/reports/*"})
public class ReportsController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!Servlet.isLogged(req)){
            resp.sendRedirect("/auth/login");
        }else {
            Integer operation = getOperation(req);
            if (operation == 1) {
                req.setAttribute("roomTypeList", RoomType.findAll());
                req.setAttribute("accessLevelList", AccessLevel.values());
                req.getRequestDispatcher("/WEB-INF/reports.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!Servlet.isLogged(req)){
            resp.sendRedirect("/auth/login");
        }else {
            Integer operation = getOperation(req);
            Connection connection = null;
            if (operation == 2){
                String reportName = "RoomsReport.pdf";
                HashMap params = new HashMap();
                params.put("roomType", Integer.valueOf(req.getParameter("id_room_type")) );

                String reportPath = getServletContext().getRealPath("/WEB-INF/reports/rooms.jasper");
                try {
                    connection = DB.getConnection();
                    JasperPrint jp = JasperFillManager.fillReport(reportPath, params, connection);
                    byte[] report = JasperExportManager.exportReportToPdf(jp);
                    resp.setHeader("Content-Disposition", "attachment;filename=" + reportName);
                    resp.getOutputStream().write(report);
                } catch (JRException e) {
                    e.printStackTrace();
                }finally {
                    DB.closeConnection(connection);
                }
            }else if (operation == 3){

            }else if (operation == 4){
                String reportName = "RoomTypesReport.pdf";
                HashMap params = new HashMap();
                params.put("minDailyPrice", new BigDecimal(req.getParameter("min_daily_price") ));
                params.put("maxDailyPrice", new BigDecimal(req.getParameter("max_daily_price") ));

                String reportPath = getServletContext().getRealPath("/WEB-INF/reports/roomTypes.jasper");
                try {
                    connection = DB.getConnection();
                    JasperPrint jp = JasperFillManager.fillReport(reportPath, params, connection);
                    byte[] report = JasperExportManager.exportReportToPdf(jp);
                    resp.setHeader("Content-Disposition", "attachment;filename=" + reportName);
                    resp.getOutputStream().write(report);
                } catch (JRException e) {
                    e.printStackTrace();
                }finally {
                    DB.closeConnection(connection);
                }
            }else if (operation == 5){

            }else if (operation == 6){

            }else if (operation == 7){
                String reportName = "StaffReport.pdf";
                HashMap params = new HashMap();
                params.put("accessLevel", req.getParameter("access_level").toLowerCase());
                String reportPath = getServletContext().getRealPath("/WEB-INF/reports/staff.jasper");
                try {
                    connection = DB.getConnection();
                    JasperPrint jp = JasperFillManager.fillReport(reportPath, params, connection);
                    byte[] report = JasperExportManager.exportReportToPdf(jp);
                    resp.setHeader("Content-Disposition", "attachment;filename=" + reportName);
                    resp.getOutputStream().write(report);
                } catch (JRException e) {
                    e.printStackTrace();
                }finally {
                    DB.closeConnection(connection);
                }
            }
        }
    }

    private Integer getOperation(HttpServletRequest request) {
        String url = request.getPathInfo();
        if (url == null) {
            url = "/";
        }
        Pattern pattern = Pattern.compile("(\\/)$|(\\/rooms$)|(\\/payments)|(\\/roomtypes)|(\\/bookings)|(\\/guests)|(\\/staff)");
        Matcher matcher = pattern.matcher(url);

        /*
            group 0 = full match
            1 = /reports
            2 = /reports/rooms
            3 = /reports/payments
            4 = /reports/roomtypes
            5 = /reports/bookings
            6 = /reports/guests
            7 = /reports/staff
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
            }else if (matcher.group(5) != null) {
                return 5;
            }else if (matcher.group(6) != null) {
                return 6;
            }else if (matcher.group(7) != null) {
                return 7;
            }
        }
        return -1;
    }
}
