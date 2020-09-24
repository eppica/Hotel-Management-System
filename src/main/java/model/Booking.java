package model;

import dao.BookingDAO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Booking {
    private Integer id;
    private Integer idRoom;
    private Room room;
    private Integer idGuest;
    private Guest guest;
    private LocalDate arrival;
    private LocalDate departure;
    private BigDecimal total;
    private Integer idStaff;
    private Staff staff;
    private static BookingDAO DAO = new BookingDAO();

    public Booking(Integer idRoom, Integer idGuest, LocalDate arrival, LocalDate departure, BigDecimal total, Integer idStaff) {
        this.idRoom = idRoom;
        this.idGuest = idGuest;
        this.arrival = arrival;
        this.departure = departure;
        this.total = total;
        this.idStaff = idStaff;
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
                this.idRoom = Integer.valueOf(add[1]);
            }
            if(add[0].equals("id_guest")){
                this.idGuest = Integer.valueOf(add[1]);
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
                this.idStaff = Integer.valueOf(add[1]);
            }
        }
    }

    public Booking(ResultSet resultSet){
        try{
            this.id = resultSet.getInt("id");
            this.idRoom = resultSet.getInt("room_fk");
            this.idGuest = resultSet.getInt("guest_fk");
            this.arrival = resultSet.getDate("arrival").toLocalDate();
            this.departure = resultSet.getDate("departure").toLocalDate();
            this.total = resultSet.getBigDecimal("total");
            this.idStaff = resultSet.getInt("staff_fk");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Booking(HttpServletRequest request) {
        if(request.getParameter("id_room").isEmpty()){
            this.idRoom = null;
        }else{
            this.idRoom = Integer.valueOf(request.getParameter("id_room"));
        }

        if(request.getParameter("id_guest").isEmpty()){
            this.idGuest = null;
        }else{
            this.idGuest = Integer.valueOf(request.getParameter("id_guest"));
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
            this.idStaff = null;
        }else{
            this.idStaff = Integer.valueOf(request.getParameter("id_staff"));
        }
    }

    public Integer getId() {
        return id;
    }

    public Booking setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getIdRoom() {
        return idRoom;
    }

    public Booking setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
        return this;
    }

    public Room getRoom() {
        if(room == null){
            room = Room.find(idRoom);
        }
        return room;
    }

    public Booking setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Integer getIdGuest() {
        return idGuest;
    }

    public Booking setIdGuest(Integer idGuest) {
        this.idGuest = idGuest;
        return this;
    }

    public Guest getGuest() {
        if(guest == null){
            guest = Guest.find(idGuest);
        }
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

    public Integer getIdStaff() {
        return idStaff;
    }

    public Booking setIdStaff(Integer idStaff) {
        this.idStaff = idStaff;
        return this;
    }

    public Staff getStaff() {
        if(staff == null){
            staff = Staff.find(idStaff);
        }
        return staff;
    }

    public Booking setStaff(Staff staff) {
        this.staff = staff;
        return this;
    }

    public String getStatus() {
        List<Check> checkList = Check.findAll(this.getId());
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
                return DAO.save(booking);
            } else {
                throw new RuntimeException("Arrival date is bigger than Departure date");
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Booking find(Integer id){
        return DAO.find(id);
    }

    public static List<Booking> findAll(){
        return DAO.findAll();
    }

    public static Booking findBookedRoom(Integer id){
        return DAO.findBookedRoom(id);
    }

    public static void update(Booking booking){
        try {
            if (booking.validate()) {
                DAO.update(booking);;
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
                return DAO.save(this);
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
                ", idRoom=" + idRoom +
                ", room=" + room +
                ", idGuest=" + idGuest +
                ", guest=" + guest +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", total=" + total +
                ", idStaff=" + idStaff +
                ", staff=" + staff +
                '}';
    }

    private Boolean validate(){
        if(this.arrival.isAfter(this.departure) || this.arrival.isEqual(this.departure)){
            if(this.arrival.isBefore(LocalDate.now())){
                return false;
            }
        }
        return true;
    }

    public static List<Booking> findAllArrival(){
        return DAO.findAll("WHERE arrival <= '" + LocalDate.now().toString() + "' AND booking.id NOT IN (SELECT booking_fk FROM `check`)");
    }

    public static List<Booking> findAllDeparture(){
        return DAO.findAll("WHERE departure = '" + LocalDate.now().toString() + "' AND booking.id IN (SELECT booking_fk FROM `check` WHERE status = 1) AND booking.id NOT IN (SELECT booking_fk FROM `check` WHERE status = 0)");
    }

    public static List<Booking> findAllGuest(Integer id){
        return DAO.findAll("WHERE booking.guest_fk = " + id );
    }


}
