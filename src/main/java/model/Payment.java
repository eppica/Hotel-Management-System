package model;

import dao.GenericDAO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Entity
public class Payment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @OneToOne
    private Booking booking;
    private LocalDateTime payTime;
    @ManyToOne
    private Staff staff;
    private static GenericDAO DAO = new GenericDAO(Payment.class);

    public Payment(Integer id, BigDecimal value, PaymentMethod paymentMethod, Booking booking,LocalDateTime payTime, Staff staff) {
        this.id = id;
        this.value = value;
        this.paymentMethod = paymentMethod;
        this.booking = booking;
        this.payTime = payTime;
        this.staff = staff;
    }

    public Payment() {
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

        if(request.getParameter("id_booking").isEmpty()){
            this.booking = null;
        }else{
            this.booking = Booking.find(Integer.valueOf(request.getParameter("id_booking")));
        }

        if(request.getParameter("pay_time").isEmpty()){
            this.payTime = null;
        }else{
            this.payTime = LocalDateTime.parse(request.getParameter("pay_time"));
        }

        if(request.getParameter("id_staff").isEmpty()){
            this.staff = null;
        }else{
            this.staff = Staff.find(Integer.valueOf(request.getParameter("id_staff")));
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
            if(add[0].equals("id_booking")){
                this.booking = Booking.find(Integer.valueOf(add[1]));
            }
            if(add[0].equals("pay_time")){
                this.payTime = LocalDateTime.parse(add[1]);
            }
            if(add[0].equals("id_staff")){
                this.staff = Staff.find(Integer.valueOf(add[1]));
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

    public Staff getStaff() {
        return staff;
    }

    public Payment save(){
        return (Payment) DAO.save(this);
    }

    public void update(){
        DAO.update(this);
    }

    public static Payment save(Payment payment){
        return (Payment) DAO.save(payment);
    }

    public static Payment find(Integer id){
        return (Payment) DAO.find(id);
    }

    public static List<Payment> findAll(){
        return DAO.findAll();
    }

    public static List<Payment> findAll(Integer id){
        return DAO.findAll("WHERE booking_id = " + id);
    }

    public static BigDecimal sumAll(Integer id){
        BigDecimal sum =  DAO.sumAll("value","WHERE booking_id = "+id);
        return sum != null ? sum.setScale(2, RoundingMode.CEILING) : new BigDecimal(0);
    }

    public static BigDecimal sumAll(String args){
        BigDecimal sum = DAO.sumAll("value", args);
        return sum != null ? sum.setScale(2, RoundingMode.CEILING) : new BigDecimal(0);
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
                ", booking=" + booking +
                '}';
    }
}
