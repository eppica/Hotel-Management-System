package dao;

import model.Guest;

import java.sql.*;
import java.util.*;

public class GuestDAO implements DAO<Guest>{
    @Override
    public Guest save(Guest object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO guest SET " +
                            "name = ?, " +
                            "document = ?, " +
                            "birth_date = ?, " +
                            "email = ?, " +
                            "phone_number = ?", Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, object.getName());
            statement.setObject(2, object.getDocument());
            statement.setObject(3, object.getBirthDate().toString());
            statement.setObject(4, object.getEmail());
            statement.setObject(5, object.getPhoneNumber());
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
    public Guest find(Integer id) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        Guest guest = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM guest WHERE  id = " + id);
            resultSet.first();
            guest = new Guest(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return guest;
    }

    @Override
    public List<Guest> findAll() {
        return findAll("");
    }

    @Override
    public void update(Guest object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE guest SET " +
                    "name = IFNULL(?, name), " +
                    "document = IFNULL(?, document), " +
                    "birth_date = IFNULL(?, email), " +
                    "email = IFNULL(?, email), " +
                    "phone_number = IFNULL(?, phone_number) " +
                    "WHERE id = " + object.getId());
            statement.setObject(1, object.getName());
            statement.setObject(2, object.getDocument());
            statement.setObject(3, object.getBirthDate());
            statement.setObject(4, object.getEmail());
            statement.setObject(5, object.getPhoneNumber());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection,statement);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("DELETE FROM guest WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
    }

    @Override
    public List<Guest> findAll(String args) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        List<Guest> guestList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM guest " + args);
            while (resultSet.next()){
                guestList.add(new Guest(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return guestList;
    }
}
