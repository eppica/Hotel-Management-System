package model;

import dao.GenericDAO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private RoomType roomType;
    private Integer number;
    private static GenericDAO DAO = new GenericDAO(Room.class);

    public Room(RoomType roomType, Integer number) {
        this.roomType = roomType;
        this.number = number;
    }

    public Room() {
    }

    public Room(String[] data) {
        for (String x : data) {
            String[] add = x.split("=");
            if (add.length == 1) {
                continue;
            }
            if (add[0].equals("id_room_type")) {
                this.roomType = RoomType.find(Integer.valueOf(add[1]));
            }
            if (add[0].equals("number")) {
                this.number = Integer.valueOf(add[1]);
            }
        }
    }

    public Room(HttpServletRequest request) {
        if (request.getParameter("id_room_type").isEmpty()) {
            this.roomType = null;
        } else {
            this.roomType = RoomType.find(Integer.valueOf(request.getParameter("id_room_type")));
        }

        if (request.getParameter("number").isEmpty()) {
            this.number = null;
        } else {
            this.number = Integer.valueOf(request.getParameter("number"));
        }
    }

    public Integer getId() {
        return id;
    }

    public Room setId(Integer id) {
        this.id = id;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public String getAvailability() {

        if (DAO.findAll("WHERE id = " + this.getId() + " AND id IN (SELECT room FROM Booking  WHERE id IN (SELECT booking FROM Checks WHERE status = 1) AND id NOT IN (SELECT booking FROM Checks WHERE status = 0))") != null) {
            if (!DAO.findAll("WHERE id = " + this.getId() + " AND id IN (SELECT room FROM Booking  WHERE id IN (SELECT booking FROM Checks WHERE status = 1) AND id NOT IN (SELECT booking FROM Checks WHERE status = 0))").isEmpty()) {
                return "Occupied";
            }
        }
        if(DAO.findAll("WHERE id = " + this.getId() + " AND id IN (SELECT room FROM Booking WHERE id NOT IN (SELECT booking FROM Checks ))") != null) {
            if (!DAO.findAll("WHERE id = " + this.getId() + " AND id IN (SELECT room FROM Booking WHERE id NOT IN (SELECT booking FROM Checks ))").isEmpty()) {
                return "Booked";
            }
        }
        return "Available";
    }

    public Room setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public Room setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public static Room save(Room room) {
        return (Room) DAO.save(room);
    }

    public static Room find(Integer id) {
        return (Room) DAO.find(id);
    }

    public static List<Room> findAll() {
        return DAO.findAll();
    }

    public static Integer countAll(String args) {
        return DAO.countAll("id", args);
    }

    public static List<Room> findAll(String args) {
        return DAO.findAll(args);
    }

    public static void update(Room room) {
        DAO.update(room);
    }

    public static void delete(Integer id) {
        DAO.delete(id);
    }

    public Room save() {
        return (Room) DAO.save(this);
    }

    public void update() {
        DAO.update(this);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType=" + roomType +
                ", number=" + number +
                '}';
    }

    public String toJSON() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"number\":\"" + number + "\"" +
                "}";
    }
}
