package dao;
/*
import model.Room;
import model.RoomType;

import java.sql.*;
import java.util.*;

public class RoomTypeDAO implements DAO<RoomType> {

    @Override
    public RoomType save(RoomType object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO room_type SET " +
                            "name = ?," +
                            "description = ?," +
                            "daily_price = ?", Statement.RETURN_GENERATED_KEYS);

            statement.setObject(1, object.getName());
            statement.setObject(2, object.getDescription());
            statement.setObject(3, object.getDailyPrice());
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
    public RoomType find(Integer id) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        RoomType roomType = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet= statement.executeQuery("SELECT * FROM room_type WHERE id = " + id);
            resultSet.first();
            roomType = new RoomType(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }

        return roomType;
    }

    @Override
    public List<RoomType> findAll() {
        return findAll("");
    }

    @Override
    public void update(RoomType object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("UPDATE room_type SET " +
                    "name = IFNULL(?, name), " +
                    "description = IFNULL(?, description), " +
                    "daily_price = IFNULL(?, daily_price) " +
                    "WHERE id = " + object.getId());
            statement.setObject(1, object.getName());
            statement.setObject(2, object.getDescription());
            statement.setObject(3, object.getDailyPrice());
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
        try{
            statement = connection.createStatement();
            statement.execute("DELETE FROM room_type WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
    }

    @Override
    public List<RoomType> findAll(String args) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        List<RoomType> roomTypeList = new ArrayList<>();
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM room_type " + args);
            while (resultSet.next()){
                roomTypeList.add(new RoomType(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return roomTypeList;
    }
}
*/