package model;

import dao.GenericDAO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private BigDecimal dailyPrice;
    private static GenericDAO DAO = new GenericDAO(RoomType.class);

    public RoomType(String name, String description, BigDecimal dailyPrice) {
        this.name = name;
        this.description = description;
        this.dailyPrice = dailyPrice;
    }

    public RoomType() {
    }

    public RoomType(String[] data){
        for(String x : data){
            String[] add = x.split("=");
            if(add.length == 1){
                continue;
            }
            if(add[0].equals("name")){
                this.name = add[1];
            }
            if(add[0].equals("description")){
                this.description = add[1].replaceAll("[\\n\\t]", "");
            }

            if(add[0].equals("daily_price")){
                this.dailyPrice = new BigDecimal(add[1]);
            }
        }
    }

    public RoomType(HttpServletRequest request){
        if(request.getParameter("name").isEmpty()){
            this.name = null;
        }else{
            this.name = request.getParameter("name");
        }

        if(request.getParameter("description").isEmpty()){
            this.name = null;
        }else{
            this.description = request.getParameter("description").replaceAll("[\\n\\t]", "");
        }

        if(request.getParameter("daily_price").isEmpty()){
            this.dailyPrice = null;
        }else{
            this.dailyPrice = new BigDecimal(request.getParameter("daily_price"));
        }
    }

    public Integer getId() {
        return id;
    }

    public RoomType setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoomType setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RoomType setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice.setScale(2, RoundingMode.CEILING);
    }

    public RoomType setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
        return this;
    }

    public static RoomType save(RoomType roomType){
        return (RoomType) DAO.save(roomType);
    }

    public static RoomType find(Integer id){
        return (RoomType) DAO.find(id);
    }

    public static List<RoomType> findAll(){
        return DAO.findAll();
    }

    public static List<RoomType> findAll(String args){
        return DAO.findAll(args);
    }

    public static void update(RoomType roomType){
        DAO.update(roomType);
    }

    public static void delete(Integer id){
        DAO.delete(id);
    }

    public RoomType save(){
        return (RoomType) DAO.save(this);
    }

    public void update(){
        DAO.update(this);
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dailyPrice=" + dailyPrice +
                '}';
    }

    public String toJSON(){
        return "{" +
                "\"id\":\"" + id + "\""+
                ", \"name\":\"" + name +"\""+
                ", \"description\":\"" + description +"\""+
                ", \"dailyPrice\":\"" + dailyPrice + "\""+
                "}";
    }
}
