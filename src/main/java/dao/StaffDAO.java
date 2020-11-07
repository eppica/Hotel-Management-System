package dao;
/*
import model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO implements DAO<Staff>{
    @Override
    public Staff save(Staff object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO staff SET " +
                    "name = ?, " +
                    "access_level = ?, " +
                    "login = ?, " +
                    "password = ?", Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, object.getName());
            statement.setObject(2, object.getAccessLevel().toString());
            statement.setObject(3, object.getLogin());
            statement.setObject(4, object.getPassword());
            statement.execute();
            try (ResultSet getGeneratedKeys = statement.getGeneratedKeys()) {
                getGeneratedKeys.first();
                object.setId(getGeneratedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return object;
    }

    @Override
    public Staff find(Integer id) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        Staff staff = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM staff WHERE id = " + id);
            resultSet.first();
            staff = new Staff(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return staff;
    }

    @Override
    public List<Staff> findAll() {
        return findAll("");
    }

    @Override
    public void update(Staff object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE staff SET " +
                    "name = IFNULL(?, name), " +
                    "access_level = IFNULL(?, access_level), " +
                    "login = IFNULL(?, login), " +
                    "password = IFNULL(?, password) " +
                    "WHERE id = " + object.getId());
            statement.setObject(1, object.getName());
            statement.setObject(2, object.getAccessLevel().toString());
            statement.setObject(3, object.getLogin());
            statement.setObject(4, object.getPassword());
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
            statement.execute("DELETE FROM staff WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
    }

    public Staff authenticate(String login, String password) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        Staff staff = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM staff WHERE login =  '" + login + "' AND password = '" + password + "'");
            resultSet.first();
            staff = new Staff(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return staff;
    }

    @Override
    public List<Staff> findAll(String args) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        List<Staff> staffList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM staff "+ args);
            while (resultSet.next()){
                staffList.add(new Staff(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return staffList;
    }
}
*/