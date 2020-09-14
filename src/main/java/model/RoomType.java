package model;

import dao.RoomTypeDAO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomType {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal dailyPrice;
    private static RoomTypeDAO DAO = new RoomTypeDAO();

    public RoomType(String name, String description, BigDecimal dailyPrice) {
        this.name = name;
        this.description = description;
        this.dailyPrice = dailyPrice;
    }

    public RoomType() {
    }

    public RoomType(String[] data){
        Pattern pattern = Pattern.compile("(\\w+)(=)([\\w\\-\\+]*)");
        for(String x : data){
            Matcher matcher = pattern.matcher(x);
            if(matcher.matches()){
                if(matcher.group(1).equals("name")){
                    this.name = matcher.group(3).replace('+', ' ');
                }else if(matcher.group(1).equals("description")){
                    this.description = matcher.group(3).replace('+', ' ');
                }else if(matcher.group(1).equals("daily_price")){
                    this.dailyPrice = new BigDecimal(matcher.group(3));
                }
            }
        }
    }

    public RoomType(ResultSet resultSet){
        try {
            this.id = resultSet.getInt("id");
            this.name = resultSet.getString("name");
            this.description = resultSet.getString("description");
            this.dailyPrice = resultSet.getBigDecimal("daily_price");
        }catch (SQLException e){
            e.printStackTrace();
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
            this.description = request.getParameter("description");
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
        return dailyPrice;
    }

    public RoomType setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
        return this;
    }

    public static RoomType save(RoomType roomType){
        return DAO.save(roomType);
    }

    public static RoomType find(Integer id){
        return DAO.find(id);
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
        return DAO.save(this);
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
                '}';
    }
}
