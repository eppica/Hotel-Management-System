package model;

import dao.GenericDAO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Guest{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String document;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private static GenericDAO DAO = new GenericDAO(Guest.class);

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
        for(String x : data){
            String[] add = x.split("=");
            if(add.length == 1){
                continue;
            }
            if(add[0].equals("name")){
                this.name = add[1];
            }
            if(add[0].equals("document")){
                this.document = add[1];
            }
            if(add[0].equals("birth_date")){
                this.birthDate = LocalDate.parse(add[1]);
            }
            if(add[0].equals("email")){
                this.email = add[1];
            }
            if(add[0].equals("phone_number")){
                this.phoneNumber = add[1];
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
        return (Guest) DAO.save(guest);
    }

    public static Guest find(Integer id){
        return (Guest) DAO.find(id);
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
        return (Guest) DAO.save(this);
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
