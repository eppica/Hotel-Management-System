package model;

import dao.GenericDAO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Entity
@NamedNativeQuery(name = "authenticate", query = "SELECT * FROM Staff WHERE login = :login AND password = :password", resultClass = Staff.class)
public class Staff{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;
    private String login;
    private String password;
    private static GenericDAO DAO = new GenericDAO(Staff.class);

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
        return (Staff) DAO.save(staff);
    }

    public static Staff find(Integer id){
        return (Staff) DAO.find(id);
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
        return (Staff) DAO.save(this);
    }

    public void update(){
        DAO.update(this);
    }

    public static Boolean authenticate(HttpServletRequest request){
        HashMap<String, Object> params = new HashMap<>();
        params.put("login", request.getParameter("login"));
        params.put("password", request.getParameter("password"));

        if(Staff.findAll().isEmpty()){
            Staff staff = new Staff("ADMIN - SHOULD BE DELETED OR UPDATED", AccessLevel.OWNER, "admin", "admin");
            staff.save();
        }

        Staff staff = (Staff) DAO.executeNamedQuery("authenticate",params);

        if(staff != null) {
            if (staff.id != null) {
                request.getSession().setAttribute("sessionStaff", staff);
                return true;
            }
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
