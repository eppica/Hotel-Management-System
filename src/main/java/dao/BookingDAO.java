package dao;
/*
import model.Booking;

import java.sql.*;
import java.util.*;

public class BookingDAO extends GenericDAO {
    @Override
    public Booking save(Booking object) {
       


    }

    @Override
    public Booking find(Integer id) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        Booking booking = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM booking WHERE  id = " + id);
            resultSet.first();
            booking = new Booking(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return booking;
    }

    @Override
    public List<Booking> findAll() {
        return findAll("");
    }

    @Override
    public void update(Booking object) {
        Connection connection = DB.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE booking SET " +
                    "room_fk = IFNULL(?, room_fk), " +
                    "guest_fk = IFNULL(?, guest_fk), " +
                    "arrival = IFNULL(?, arrival), " +
                    "departure = IFNULL(?, departure), " +
                    "total = IFNULL(?, total), " +
                    "staff_fk = IFNULL(?, staff_fk) " +
                    "WHERE id = " + object.getId());
            statement.setObject(1, object.getIdRoom());
            statement.setObject(2, object.getIdGuest());
            statement.setObject(3, object.getArrival());
            statement.setObject(4, object.getDeparture());
            statement.setObject(5, object.getTotal());
            statement.setObject(6, object.getIdStaff());
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
            statement.execute("DELETE FROM booking WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
    }

    @Override
    public List<Booking> findAll(String args) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        List<Booking>bookingList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM booking " + args);
            while (resultSet.next()){
                bookingList.add(new Booking(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }

        return bookingList;
    }

    public Booking findBookedRoom(Integer id) {
        Connection connection = DB.getConnection();
        Statement statement = null;
        Booking booking = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM booking WHERE room_fk = " + id + " AND id NOT IN (SELECT booking_fk FROM `check` WHERE status = 0)");
            if (resultSet.first()){
                resultSet.first();
                booking = new Booking(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeConnection(connection, statement);
        }
        return booking;
    }
}
*/
