package model;

import dao.GenericDAO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Checks{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime check;
    private Integer idStaff;
    @ManyToOne
    private Staff staff;
    private Integer idBooking;
    @ManyToOne
    private Booking booking;
    private Boolean status;
    private static GenericDAO DAO = new GenericDAO(Checks.class);

    public Checks(LocalDateTime check, Integer idStaff, Integer idBooking, Boolean status) {
        this.check = check;
        this.idStaff = idStaff;
        this.idBooking = idBooking;
        this.status = status;
    }

    public Checks() {
    }

    public Checks(String[] data){
        for(String x : data){
            String[] add = x.split("=");
            if(add.length == 1){
                continue;
            }
            if(add[0].equals("check")){
                this.check = LocalDateTime.parse(add[1]);
            }
            if(add[0].equals("id_guest")){
                this.idStaff = Integer.valueOf(add[1]);
            }
            if(add[0].equals("id_booking")){
                this.idBooking = Integer.valueOf(add[1]);
            }
            if(add[0].equals("status")){
                this.status = Boolean.valueOf(add[1]);
            }
        }
    }

    public Checks(ResultSet resultSet){
        try{
            this.id = resultSet.getInt("id");
            this.check = resultSet.getTimestamp("check").toLocalDateTime();
            this.idStaff = resultSet.getInt("staff_fk");
            this.idBooking = resultSet.getInt("booking_fk");
            if(resultSet.getBoolean("status")){
                this.status = true;
            }else{
                this.status = false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Checks(HttpServletRequest request) {
        if(request.getParameter("check").isEmpty()){
            this.check = null;
        }else{
            this.check = LocalDateTime.parse(request.getParameter("check"));
        }

        if(request.getParameter("id_staff").isEmpty()){
            this.idStaff = null;
        }else{
            this.idStaff = Integer.valueOf(request.getParameter("id_staff"));
        }

        if(request.getParameter("id_booking").isEmpty()){
            this.idBooking = null;
        }else{
            this.idBooking = Integer.valueOf(request.getParameter("id_booking"));
        }

        if(request.getParameter("status").isEmpty()){
            this.status = null;
        }else{
            if(request.getParameter("status").equals("1")){
                this.status = true;
            }else{
                this.status = false;
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public Checks setId(Integer id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCheck() {
        return check;
    }

    public Checks setCheck(LocalDateTime check) {
        this.check = check;
        return this;
    }

    public Integer getIdStaff() {
        return idStaff;
    }

    public Checks setIdStaff(Integer idStaff) {
        this.idStaff = idStaff;
        return this;
    }

    public Staff getStaff() {
        if(staff == null){
            staff = Staff.find(idStaff);
        }
        return staff;
    }

    public Checks setStaff(Staff staff) {
        this.staff = staff;
        return this;
    }

    public Integer getIdBooking() {
        return idBooking;
    }

    public Checks setIdBooking(Integer idBooking) {
        this.idBooking = idBooking;
        return this;
    }

    public Booking getBooking() {
        if(booking == null){
            booking = Booking.find(idBooking);
        }
        return booking;
    }

    public Checks setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public Checks setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public static Checks save(Checks check){
        return (Checks) DAO.save(check);
    }

    public static Checks find(Integer id){
        return (Checks) DAO.find(id);
    }

    public static List<Checks> findAll(){
        return DAO.findAll();
    }

    public static List<Checks> findAll(Integer id){
        return DAO.findAll("WHERE booking_fk = " + id);
    }

    public static void update(Checks check){
        DAO.update(check);
    }

    public static void delete(Integer id){
        DAO.delete(id);
    }

    public Checks save(){
        return (Checks) DAO.save(this);
    }

    public void update(){
        DAO.update(this);
    }

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", check=" + check +
                ", idStaff=" + idStaff +
                ", staff=" + staff +
                ", idBooking=" + idBooking +
                ", booking=" + booking +
                ", status=" + status +
                '}';
    }
}
