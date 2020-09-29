package model;

import dao.PaymentDAO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Payment {
    private Integer id;
    private BigDecimal value;
    private PaymentMethod paymentMethod;
    private Integer idBooking;
    private Booking booking;
    private LocalDateTime payTime;
    private Integer idStaff;
    private Staff staff;
    private static PaymentDAO DAO = new PaymentDAO();

    public Payment(Integer id, BigDecimal value, PaymentMethod paymentMethod, Integer idBooking,LocalDateTime payTime, Integer idStaff) {
        this.id = id;
        this.value = value;
        this.paymentMethod = paymentMethod;
        this.idBooking = idBooking;
        this.payTime = payTime;
        this.idStaff = idStaff;
    }

    public Payment() {
    }

    public Payment(ResultSet resultSet) {
        try{
            this.id = resultSet.getInt("id");
            this.value = resultSet.getBigDecimal("value");
            this.paymentMethod = PaymentMethod.valueOf(resultSet.getString("method"));
            this.idBooking = resultSet.getInt("booking_fk");
            this.payTime = resultSet.getTimestamp("pay_time").toLocalDateTime();
            this.idStaff = resultSet.getInt("staff_fk");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Payment(HttpServletRequest request){
        if(request.getParameter("value").isEmpty()){
            this.value = null;
        }else {
            this.value = new BigDecimal(request.getParameter("value"));
        }

        if(request.getParameter("payment_method").isEmpty()){
            this.paymentMethod = null;
        }else{
            this.paymentMethod = PaymentMethod.valueOf(request.getParameter("payment_method"));
        }

        if(request.getParameter("idbooking").isEmpty()){
            this.idBooking = null;
        }else{
            this.idBooking = Integer.valueOf(request.getParameter("idbooking"));
        }

        if(request.getParameter("pay_time").isEmpty()){
            this.payTime = null;
        }else{
            this.payTime = LocalDateTime.parse(request.getParameter("pay_time"));
        }

        if(request.getParameter("id_staff").isEmpty()){
            this.idStaff = null;
        }else{
            this.idStaff = Integer.valueOf(request.getParameter("id_staff"));
        }
    }

    public Payment(String[] data){
        for(String x : data){
            String[] add = x.split("=");
            if(add.length == 1){
                continue;
            }
            if(add[0].equals("value")){
                this.value = new BigDecimal(add[1]);
            }
            if(add[0].equals("payment_method")){
                this.paymentMethod = PaymentMethod.valueOf(add[1]);
            }
            if(add[0].equals("id_pay_booking")){
                this.idBooking = Integer.valueOf(add[1]);
            }
            if(add[0].equals("pay_time")){
                this.payTime = LocalDateTime.parse(add[1]);
            }
            if(add[0].equals("id_staff")){
                this.idStaff = Integer.valueOf(add[1]);
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value.setScale(2, RoundingMode.CEILING);
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(Integer idBooking) {
        this.idBooking = idBooking;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public Integer getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(Integer idStaff) {
        this.idStaff = idStaff;
    }

    public Staff getStaff() {
        return Staff.find(this.idStaff);
    }

    public Payment save(){
        return DAO.save(this);
    }

    public void update(){
        DAO.update(this);
    }

    public static Payment save(Payment payment){
        return DAO.save(payment);
    }

    public static Payment find(Integer id){
        return DAO.find(id);
    }

    public static List<Payment> findAll(){
        return DAO.findAll();
    }

    public static List<Payment> findAll(Integer id){
        return DAO.findAll("WHERE booking_fk = " + id);
    }

    public static BigDecimal sumAll(Integer id){
        return DAO.sumAll(id).setScale(2, RoundingMode.CEILING);
    }

    public static BigDecimal sumAll(String args){
        return DAO.sumAll(args).setScale(2, RoundingMode.CEILING);
    }

    public static void update(Payment payment){
        DAO.update(payment);
    }

    public static void delete(Integer id){
        DAO.delete(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", value=" + value +
                ", paymentMethod=" + paymentMethod +
                ", idBooking=" + idBooking +
                ", booking=" + booking +
                '}';
    }
}
