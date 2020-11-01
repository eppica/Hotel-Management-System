package model;

import dao.StaffDAO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Entity
public class Staff {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;
    private String login;
    private String password;
    private static StaffDAO DAO = new StaffDAO();

    public Staff(String name, AccessLevel accessLevel, String login, String password) {
        this.name = name;
        this.accessLevel = accessLevel;
        this.login = login;
        this.password = password;
    }

    public Staff() {
    }

    public Staff(HttpServletRequest request) {
        if(request.getParameter("name").isEmpty()){
            this.name = null;
        }else {
            this.name = request.getParameter("name");
        }

        if(request.getParameter("access_level").isEmpty()){
            this.accessLevel = null;
        }else{
            this.accessLevel = AccessLevel.valueOf(request.getParameter("access_level"));
        }

        if(request.getParameter("login").isEmpty()){
            this.login = null;
        }else{
            this.login = request.getParameter("login");
        }

        if(request.getParameter("password").isEmpty()){
            this.password = null;
        }else{
            this.password = request.getParameter("password");
        }
    }

    public Staff(ResultSet resultSet){
        try{
            this.id = resultSet.getInt("id");
            this.name = resultSet.getString("name");
            this.accessLevel = AccessLevel.valueOf(resultSet.getString("access_level"));
            this.login = resultSet.getString("login");
            this.password = resultSet.getString("password");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Staff(String[] data){
        for(String x : data){
            String[] add = x.split("=");
            if(add.length == 1){
                continue;
            }
            if(add[0].equals("name")){
                this.name = add[1];
            }
            if(add[0].equals("access_level")){
                this.accessLevel = AccessLevel.valueOf(add[1]);
            }
            if(add[0].equals("login")){
                this.login = add[1];
            }
            if(add[0].equals("password")){
                this.password = add[1];
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public Staff setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Staff setName(String name) {
        this.name = name;
        return this;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public Staff setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public Staff setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Staff setPassword(String password) {
        this.password = password;
        return this;
    }

    public static Staff save(Staff staff){
        return DAO.save(staff);
    }

    public static Staff find(Integer id){
        return DAO.find(id);
    }

    public static List<Staff> findAll(){
        return DAO.findAll();
    }

    public static void update(Staff staff){
        DAO.update(staff);
    }

    public static void delete(Integer id){
        DAO.delete(id);
    }

    public Staff save(){
        return DAO.save(this);
    }

    public void update(){
        DAO.update(this);
    }

    public static Boolean authenticate(HttpServletRequest request){
        Staff staff = DAO.authenticate(request.getParameter("login"), request.getParameter("password"));
        if(staff.id != null){
            request.getSession().setAttribute("sessionStaff", staff);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accessLevel=" + accessLevel +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
