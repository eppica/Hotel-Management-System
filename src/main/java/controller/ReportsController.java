package controller;

import dao.DB;
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
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
            }else{
                Connection connection = null;
                if (operation == 5){
                    //bookings
                    LocalDateTime now = LocalDateTime.now().withNano(0);
                    String reportName = ("BookingsReport_"+now+".pdf").trim().replaceAll(":","-").replaceAll("T","_");
                    HashMap params = new HashMap();
                    String reportPath = getServletContext().getRealPath("/WEB-INF/reports/bookings.jasper");
                    try {
                        connection = DB.getConnection();
                        JasperPrint jp = JasperFillManager.fillReport(reportPath, params, connection);
                        byte[] report = JasperExportManager.exportReportToPdf(jp);
                        resp.setHeader("Content-Disposition", "attachment;filename=" + reportName);
                        resp.getOutputStream().write(report);
                        connection.close();
                    } catch (JRException | SQLException e) {
                        e.printStackTrace();
                    }

                }else if (operation == 6){
                    //guests
                    LocalDateTime now = LocalDateTime.now().withNano(0);
                    String reportName = ("GuestsReport_"+now+".pdf").trim().replaceAll(":","-").replaceAll("T","_");
                    HashMap params = new HashMap();
                    String reportPath = getServletContext().getRealPath("/WEB-INF/reports/guests.jasper");
                    try {
                        connection = DB.getConnection();
                        JasperPrint jp = JasperFillManager.fillReport(reportPath, params, connection);
                        byte[] report = JasperExportManager.exportReportToPdf(jp);
                        resp.setHeader("Content-Disposition", "attachment;filename=" + reportName);
                        resp.getOutputStream().write(report);
                        connection.close();
                    } catch (JRException | SQLException e) {
                        e.printStackTrace();
                    }

                }
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
                LocalDateTime now = LocalDateTime.now().withNano(0);
                String reportName = ("RoomsReport_"+now+".pdf").trim().replaceAll(":","-").replaceAll("T","_");
                HashMap params = new HashMap();
                params.put("RoomType", Integer.valueOf(req.getParameter("id_room_type")) );

                String reportPath = getServletContext().getRealPath("/WEB-INF/reports/rooms.jasper");
                try {
                    connection = DB.getConnection();
                    JasperPrint jp = JasperFillManager.fillReport(reportPath, params, connection);
                    byte[] report = JasperExportManager.exportReportToPdf(jp);
                    resp.setHeader("Content-Disposition", "attachment;filename=" + reportName);
                    resp.getOutputStream().write(report);
                    connection.close();
                } catch (JRException | SQLException e) {
                    e.printStackTrace();
                }
            }else if (operation == 3){
                //payments
                LocalDateTime now = LocalDateTime.now().withNano(0);
                String reportName = ("PaymentsReport_"+now+".pdf").trim().replaceAll(":","-").replaceAll("T","_");
                HashMap params = new HashMap();
                Date initialDate = (req.getParameter("initial") != "") ? Date.valueOf(req.getParameter("initial")) : null;
                Date finalDate = (req.getParameter("final") != "") ? Date.valueOf(req.getParameter("final")) : null;
                params.put("initial", initialDate );
                params.put("final", finalDate);
                String reportPath = getServletContext().getRealPath("/WEB-INF/reports/payments.jasper");
                try {
                    connection = DB.getConnection();
                    JasperPrint jp = JasperFillManager.fillReport(reportPath, params, connection);
                    byte[] report = JasperExportManager.exportReportToPdf(jp);
                    resp.setHeader("Content-Disposition", "attachment;filename=" + reportName);
                    resp.getOutputStream().write(report);
                    connection.close();
                } catch (JRException | SQLException e) {
                    e.printStackTrace();
                }

            }else if (operation == 4){
                LocalDateTime now = LocalDateTime.now().withNano(0);
                String reportName = ("RoomTypesReport_"+now+".pdf").trim().replaceAll(":","-").replaceAll("T","_");
                HashMap params = new HashMap();

                BigDecimal min = (req.getParameter("min_daily_price")!="") ? new BigDecimal(req.getParameter("min_daily_price")) : null;
                BigDecimal max = (req.getParameter("max_daily_price")!="") ? new BigDecimal(req.getParameter("max_daily_price")) : null;
                params.put("minDailyPrice", min);
                params.put("maxDailyPrice", max);

                String reportPath = getServletContext().getRealPath("/WEB-INF/reports/roomTypes.jasper");
                try {
                    connection = DB.getConnection();
                    JasperPrint jp = JasperFillManager.fillReport(reportPath, params, connection);
                    byte[] report = JasperExportManager.exportReportToPdf(jp);
                    resp.setHeader("Content-Disposition", "attachment;filename=" + reportName);
                    resp.getOutputStream().write(report);
                    connection.close();
                } catch (JRException | SQLException e) {
                    e.printStackTrace();
                }
            }else if (operation == 7){
                LocalDateTime now = LocalDateTime.now().withNano(0);
                String reportName = ("StaffReport_"+now+".pdf").trim().replaceAll(":","-").replaceAll("T","_");
                HashMap params = new HashMap();
                params.put("accessLevel", req.getParameter("access_level").toLowerCase());
                String reportPath = getServletContext().getRealPath("/WEB-INF/reports/staff.jasper");
                try {
                    connection = DB.getConnection();
                    JasperPrint jp = JasperFillManager.fillReport(reportPath, params, connection);
                    byte[] report = JasperExportManager.exportReportToPdf(jp);
                    resp.setHeader("Content-Disposition", "attachment;filename=" + reportName);
                    resp.getOutputStream().write(report);
                    connection.close();
                } catch (JRException | SQLException e) {
                    e.printStackTrace();
                }
            }else if (operation == 8){
                LocalDateTime now = LocalDateTime.now().withNano(0);
                String reportName = ("ArrivalsDeparturesReport_"+now+".pdf").trim().replaceAll(":","-").replaceAll("T","_");
                HashMap params = new HashMap();
                Date initialDate = (req.getParameter("initial") != "") ? Date.valueOf(req.getParameter("initial")) : null;
                Date finalDate = (req.getParameter("final") != "") ? Date.valueOf(req.getParameter("final")) : null;
                params.put("initial", initialDate );
                params.put("final", finalDate);

                String reportPath = getServletContext().getRealPath("/WEB-INF/reports/arrivalsDepartures.jasper");
                try {
                    connection = DB.getConnection();
                    JasperPrint jp = JasperFillManager.fillReport(reportPath, params, connection);
                    byte[] report = JasperExportManager.exportReportToPdf(jp);
                    resp.setHeader("Content-Disposition", "attachment;filename=" + reportName);
                    resp.getOutputStream().write(report);
                    connection.close();
                } catch (JRException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Integer getOperation(HttpServletRequest request) {
        String url = request.getPathInfo();
        if (url == null) {
            url = "/";
        }
        Pattern pattern = Pattern.compile("(\\/)$|(\\/rooms$)|(\\/payments)|(\\/roomtypes)|(\\/bookings)|(\\/guests)|(\\/staff)|(\\/arrivaldeparture)");
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
            8 = /reports/arrivaldeparture
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
            }else if (matcher.group(8) != null) {
                return 8;
            }
        }
        return -1;
    }
}