package dao;
/*
import model.Room;

import java.sql.*;
import java.util.*;

public class RoomDAO implements DAO<Room> {
    @Override
    public Room save(Room object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO room SET " +
                            "room_type_fk = ?," +
                            "number = ?" , Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, object.getIdRoomType());
            statement.setObject(2, object.getNumber());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.first();
                object.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return object;
    }

    @Override
    public Room find(Integer id) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        Room room = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM room WHERE id = " + id);
            resultSet.first();
            room = new Room(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return room;
    }

    @Override
    public List<Room> findAll() {
        return findAll("");
    }

    @Override
    public void update(Room object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE room SET " +
                    "room_type_fk = IFNULL(?, room_type_fk), " +
                    "number = IFNULL(?, number) " +
                    "WHERE id = " + object.getId());
            statement.setObject(1, object.getIdRoomType());
            statement.setObject(2, object.getNumber());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("DELETE FROM room WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
    }

    @Override
    public List<Room> findAll(String args) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        List<Room> roomList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM room " + args);
            while (resultSet.next()){
                roomList.add(new Room(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return roomList;
    }

    public Integer countAll(String args) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        Integer count = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(id) FROM room " + args);
            resultSet.first();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return count;
    }


}
*/