package model;

import dao.GuestDAO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Guest {
    private Integer id;
    private String name;
    private String document;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private static GuestDAO DAO = new GuestDAO();

    public Guest(String name, String document, LocalDate birthDate, String email, String phoneNumber) {
        this.name = name;
        this.document = document;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Guest() {
    }

    public Guest(String[] data){
        Pattern pattern = Pattern.compile("(\\w+)(=)([\\w\\-\\+]*)");
        for(String x : data){
            Matcher matcher = pattern.matcher(x);
            if(matcher.matches()){
                if(matcher.group(1).equals("name")){
                    this.name = matcher.group(3).replace('+', ' ');
                }else if(matcher.group(1).equals("document")){
                    this.document = matcher.group(3).replace('+', ' ');
                }else if(matcher.group(1).equals("birth_date")){
                    this.birthDate = LocalDate.parse(matcher.group(3));
                }else if(matcher.group(1).equals("email")){
                    this.email = matcher.group(3).replace('+', ' ');
                }else if(matcher.group(1).equals("phone_number")){
                    this.phoneNumber = matcher.group(3).replace('+', ' ');
                }
            }
        }
    }

    public Guest(HttpServletRequest request) {
        if(request.getParameter("name").isEmpty()){
            this.name = null;
        }else {
            this.name = request.getParameter("name");
        }

        if(request.getParameter("document").isEmpty()){
            this.document = null;
        }else{
            this.document = request.getParameter("document");
        }

        if(request.getParameter("birth_date").isEmpty()){
            this.birthDate = null;
        }else{
            this.birthDate = LocalDate.parse(request.getParameter("birth_date"));
        }

        if(request.getParameter("email").isEmpty()){
            this.email = null;
        }else {
            this.email = request.getParameter("email");
        }

        if(request.getParameter("phone_number").isEmpty()){
            this.phoneNumber = null;
        }else{
            this.phoneNumber = request.getParameter("phone_number");
        }
    }

    public Guest(ResultSet resultSet){
        try {
            this.id = resultSet.getInt("id");
            this.name = resultSet.getString("name");
            this.document = resultSet.getString("document");
            this.birthDate = resultSet.getDate("birth_date").toLocalDate();
            this.email = resultSet.getString("email");
            this.phoneNumber = resultSet.getString("phone_number");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public Guest setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Guest setName(String name) {
        this.name = name;
        return this;
    }

    public String getDocument() {
        return document;
    }

    public Guest setDocument(String document) {
        this.document = document;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Guest setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Guest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Guest setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public static Guest save(Guest guest){
        return DAO.save(guest);
    }

    public static Guest find(Integer id){
        return DAO.find(id);
    }

    public static List<Guest> findAll(){
        return DAO.findAll();
    }

    public static void update(Guest guest){
        DAO.update(guest);
    }

    public static void delete(Integer id){
        DAO.delete(id);
    }

    public Guest save(){
        return DAO.save(this);
    }

    public void update(){
        DAO.update(this);
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", document='" + document + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
