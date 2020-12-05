package model;

import dao.GenericDAO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Checks{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime checkTime;
    @ManyToOne
    private Staff staff;
    @ManyToOne
    private Booking booking;
    private Boolean status;
    private static GenericDAO DAO = new GenericDAO(Checks.class);

    public Checks(LocalDateTime checkTime, Staff staff, Booking booking, Boolean status) {
        this.checkTime = checkTime;
        this.staff = staff;
        this.booking = booking;
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
                this.checkTime = LocalDateTime.parse(add[1]);
            }
            if(add[0].equals("id_guest")){
                this.staff = Staff.find(Integer.valueOf(add[1]));
            }
            if(add[0].equals("id_booking")){
                this.booking = Booking.find(Integer.valueOf(add[1]));
            }
            if(add[0].equals("status")){
                this.status = Boolean.valueOf(add[1]);
            }
        }
    }

    public Checks(HttpServletRequest request) {
        if(request.getParameter("check").isEmpty()){
            this.checkTime = null;
        }else{
            this.checkTime = LocalDateTime.parse(request.getParameter("check"));
        }

        if(request.getParameter("id_staff").isEmpty()){
            this.staff = null;
        }else{
            this.staff = Staff.find(Integer.valueOf(request.getParameter("id_staff")));
        }

        if(request.getParameter("id_booking").isEmpty()){
            this.booking = null;
        }else{
            this.booking = Booking.find(Integer.valueOf(request.getParameter("id_booking")));
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

    public LocalDateTime getCheckTime() {
        return checkTime;
    }

    public Checks setCheckTime(LocalDateTime check) {
        this.checkTime = check;
        return this;
    }

    public Staff getStaff() {
        return staff;
    }

    public Staff setStaff(Staff staff) {
        this.staff = staff;
        return staff;
    }

    public Booking getBooking() {
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
        return DAO.findAll("WHERE booking_id = " + id);
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
        return "Checks{" +
                "id=" + id +
                ", check=" + checkTime +
                ", staff=" + staff +
                ", booking=" + booking +
                ", status=" + status +
                '}';
    }
}
