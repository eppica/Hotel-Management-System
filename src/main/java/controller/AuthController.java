package controller;

import model.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "AuthController", urlPatterns = {"/auth/*"})
public class AuthController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = getOperation(req);
        if(operation == 1){
            if(Servlet.isLogged(req)){
                resp.sendRedirect("/dashboard");
            }else{
                req.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(req, resp);
            }

        }else if(operation == 2){
            req.getSession().removeAttribute("sessionStaff");
            resp.sendRedirect("/auth/login");
        }else{
            req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer operation = getOperation(req);
        if(operation == 1){
            if(Staff.authenticate(req)){
                Servlet.resetSessionTime(req);
                resp.sendRedirect("/dashboard");
            }else {
                String message = "Invalid username or password";
                req.setAttribute("errorMessage", message);
                req.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(req, resp);
            }
        }else{

        }
    }

    private Integer getOperation(HttpServletRequest request){
        String url = request.getPathInfo();
        if(url==null){
            url = "/";
        }
        Pattern pattern = Pattern.compile("(\\/login)|(\\/logout)");
        Matcher matcher = pattern.matcher(url);

        /*
            group 0 = full match
            1 = /auth/login
            2 = /auth/logout
         */

        if(matcher.find()){
            if(matcher.group(1) != null){
                return 1;
            }else if (matcher.group(2) != null){
                return 2;
            }
        }
        return -1;
    }

}
