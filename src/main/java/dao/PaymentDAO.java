package dao;

import model.Payment;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO implements DAO<Payment>{

    @Override
    public Payment save(Payment object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO payment SET " +
                    "value = ?, " +
                    "method = ?, " +
                    "booking_fk = ?, " +
                    "pay_time = ?, " +
                    "staff_fk = ?", Statement.RETURN_GENERATED_KEYS );
            statement.setObject(1, object.getValue());
            statement.setObject(2, object.getPaymentMethod().toString());
            statement.setObject(3, object.getIdBooking());
            statement.setObject(4, object.getPayTime());
            statement.setObject(5, object.getIdStaff());
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
    public Payment find(Integer id) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        Payment payment = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM payment WHERE  id = " + id);
            resultSet.first();
            payment = new Payment(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }

        return payment;
    }

    @Override
    public List<Payment> findAll() {
        return findAll("");
    }

    public BigDecimal sumAll(Integer bookingId) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        BigDecimal value = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT SUM(value) FROM payment WHERE booking_fk = " + bookingId);
            resultSet.first();
            value = resultSet.getBigDecimal(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }

        if(value == null){
            return BigDecimal.valueOf(0);
        }
        
        return value;
    }

    @Override
    public void update(Payment object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE payment SET " +
                    "value = IFNULL(?, value), " +
                    "method = IFNULL(?, method), " +
                    "booking_fk = IFNULL(?, booking_fk)," +
                    "pay_time = IFNULL(?, pay_time)," +
                    "staff_fk = IFNULL(?, staff_fk) " +
                    "WHERE id = " + object.getId());
            statement.setObject(1, object.getValue());
            statement.setObject(2, object.getPaymentMethod().toString());
            statement.setObject(3, object.getIdBooking());
            statement.setObject(4, object.getPayTime());
            statement.setObject(5, object.getIdStaff());
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
            statement.execute("DELETE FROM payment WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
    }

    @Override
    public List<Payment> findAll(String args) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        List<Payment> paymentList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM payment " + args);
            while (resultSet.next()){
                paymentList.add(new Payment(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return paymentList;
    }


}
