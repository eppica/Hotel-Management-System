package dao;

import model.Check;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckDAO implements DAO<Check> {
    @Override
    public Check save(Check object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO `check` SET " +
                    "`check` = ?, " +
                    "staff_fk = ?, " +
                    "booking_fk = ?, " +
                    "status = ?", Statement.RETURN_GENERATED_KEYS );
            statement.setObject(1, object.getCheck().toString());
            statement.setObject(2, object.getIdStaff());
            statement.setObject(3, object.getIdBooking());
            statement.setObject(4, object.getStatus());
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
    public Check find(Integer id) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        Check check = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `check` WHERE  id = " + id);
            resultSet.first();
            check = new Check(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }

        return check;
    }

    @Override
    public List<Check> findAll() {
        return findAll("");
    }

    @Override
    public void update(Check object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE `check` SET " +
                    "`check` = IFNULL(?, `check`), " +
                    "staff_fk = IFNULL(?, staff_fk), " +
                    "booking_fk = IFNULL(?, booking_fk), " +
                    "status = IFNULL(?, status) " +
                    "WHERE id = " + object.getId());
            statement.setObject(1, object.getCheck());
            statement.setObject(2, object.getIdStaff());
            statement.setObject(3, object.getIdBooking());
            statement.setObject(4, object.getStatus());
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
            statement.execute("DELETE FROM `check` WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
    }

    @Override
    public List<Check> findAll(String args) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        List<Check> checkList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `check` "+args);
            while (resultSet.next()){
                checkList.add(new Check(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return checkList;
    }
}
