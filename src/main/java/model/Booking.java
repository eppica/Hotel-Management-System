package model;

import dao.GenericDAO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;

@Entity
@NamedNativeQuery(name = "findBookedRoom", query = "SELECT * FROM Booking B WHERE B.room_id = :id AND id NOT IN (SELECT C.booking_id FROM Checks C WHERE status = 0)", resultClass = Booking.class)
public class Booking{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Room room;
    @ManyToOne
    private Guest guest;
    private LocalDate arrival;
    private LocalDate departure;
    private BigDecimal total;
    @ManyToOne
    private Staff staff;
    private static GenericDAO DAO = new GenericDAO(Booking.class);

    public Booking(Room room, Guest guest, LocalDate arrival, LocalDate departure, BigDecimal total, Staff staff) {
        this.room = room;
        this.guest = guest;
        this.arrival = arrival;
        this.departure = departure;
        this.total = total;
        this.staff = staff;
    }

    public Booking() {
    }

    public Booking(String[] data){
        for(String x : data){
            String[] add = x.split("=");
            if(add.length == 1){
                continue;
            }
            if(add[0].equals("id_room")){
                this.room = Room.find(Integer.valueOf(add[1]));
            }
            if(add[0].equals("id_guest")){
                this.guest = Guest.find(Integer.valueOf(add[1]));
            }
            if(add[0].equals("arrival")){
                this.arrival = LocalDate.parse(add[1]);
            }
            if(add[0].equals("departure")){
                this.departure = LocalDate.parse(add[1]);
            }
            if(add[0].equals("total")){
                this.total = new BigDecimal(add[1]);
            }
            if(add[0].equals("id_staff")){
                this.staff = Staff.find(Integer.valueOf(add[1]));
            }
        }
    }

    public Booking(HttpServletRequest request) {
        if(request.getParameter("id_room").isEmpty()){
            this.room = null;
        }else{
            this.room = Room.find(Integer.valueOf(request.getParameter("id_room")));
        }

        if(request.getParameter("id_guest").isEmpty()){
            this.guest = null;
        }else{
            this.guest = Guest.find(Integer.valueOf(request.getParameter("id_guest")));
        }

        if(request.getParameter("arrival").isEmpty()){
            this.arrival = null;
        }else{
            this.arrival = LocalDate.parse(request.getParameter("arrival"));
        }

        if(request.getParameter("departure").isEmpty()){
            this.departure = null;
        }else {
            this.departure = LocalDate.parse(request.getParameter("departure"));
        }

        this.total = this.getRoom().getRoomType().getDailyPrice().multiply(new BigDecimal(Period.between(this.getArrival(), this.getDeparture()).getDays()));

        if(request.getParameter("id_staff").isEmpty()){
            this.staff = null;
        }else{
            this.staff = Staff.find(Integer.valueOf(request.getParameter("id_staff")));
        }
    }

    public Integer getId() {
        return id;
    }

    public Booking setId(Integer id) {
        this.id = id;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Booking setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Guest getGuest() {
        return guest;
    }

    public Booking setGuest(Guest guest) {
        this.guest = guest;
        return this;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public Booking setArrival(LocalDate arrival) {
        this.arrival = arrival;
        return this;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public Booking setDeparture(LocalDate departure) {
        this.departure = departure;
        return this;
    }

    public BigDecimal getTotal() {
        return total.setScale(2, RoundingMode.CEILING);
    }

    public Booking setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public Staff getStaff() {
        return staff;
    }

    public Booking setStaff(Staff staff) {
        this.staff = staff;
        return this;
    }

    public String getStatus() {
        List<Checks> checkList = Checks.findAll(this.getId());
        if(checkList.size() == 1){
            if(checkList.get(0).getStatus()){
                return "Arrived";
            }
        }else if (checkList.size() == 2){
            if((checkList.get(0).getStatus()) && (!checkList.get(1).getStatus())){
                return "Departed";
            }
        }
        return "Booked";
    }

    public static Booking save(Booking booking){
        try {
            if (booking.validate()) {
                return (Booking) DAO.save(booking);
            } else {
                throw new RuntimeException("Arrival date is bigger than Departure date");
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Booking find(Integer id){
        return (Booking) DAO.find(id);
    }

    public static List<Booking> findAll(){
        return DAO.findAll();
    }

    public static List<Booking> findAll(String args){
        return DAO.findAll(args);
    }

    public static Booking findBookedRoom(Integer id){

        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);

        return (Booking) DAO.executeNamedQuery("findBookedRoom",params);

    }

    public static void update(Booking booking){
        try {
            if (booking.validate()) {
                DAO.update(booking);
            } else {
                throw new RuntimeException("Arrival date is bigger than Departure date");
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    public static void delete(Integer id){
        DAO.delete(id);
    }

    public Booking save(){
        try {
            if (validate()) {
                return (Booking) DAO.save(this);
            } else {
                throw new RuntimeException("Arrival date is bigger than Departure date");
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public void update(){
        try {
            if (validate()) {
                DAO.update(this);
            } else {
                throw new RuntimeException("Arrival date is bigger than Departure date");
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", room=" + room +
                ", guest=" + guest +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", total=" + total +
                ", staff=" + staff +
                '}';
    }

    private Boolean validate(){
        if(this.arrival.isAfter(this.departure) || this.arrival.isEqual(this.departure)){
            return !this.arrival.isBefore(LocalDate.now());
        }
        return true;
    }

    public static List<Booking> findAllArrival(){
        return DAO.findAll("WHERE arrival <= '" + LocalDate.now().toString() + "' AND id NOT IN (SELECT booking FROM Checks)");
    }

    public static List<Booking> findAllDeparture(){
        return DAO.findAll("WHERE departure = '" + LocalDate.now().toString() + "' AND id IN (SELECT booking FROM Checks WHERE status = 1) AND id NOT IN (SELECT booking FROM Checks WHERE status = 0)");
    }

    public static List<Booking> findAllGuest(Integer id){
        return DAO.findAll("WHERE guest_id = " + id );
    }

}
