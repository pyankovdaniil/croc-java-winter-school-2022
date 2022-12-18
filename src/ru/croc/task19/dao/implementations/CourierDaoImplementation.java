package ru.croc.task19.dao.implementations;

import ru.croc.task19.dao.CourierDao;
import ru.croc.task19.dao.UpdatedUserDao;
import ru.croc.task19.updatedshopelements.Courier;
import ru.croc.task19.updatedshopelements.DeliveryOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourierDaoImplementation implements CourierDao {
    private final String databasePath;
    private final String databaseUsername;
    private final String databasePassword;
    private final Connection connection;

    public CourierDaoImplementation(String databasePath, String databaseUsername,
                                    String databasePassword, Connection connection) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.connection = connection;
    }

    @Override
    public int findCourierIdByEmployeeNumber(String employeeNumber) {
        try {
            PreparedStatement courierStatement =
                    connection.prepareStatement("select * from `courier` where employee_number=?");
            courierStatement.setString(1, employeeNumber);

            ResultSet courierSet = courierStatement.executeQuery();

            if (courierSet.next()) {
                return courierSet.getInt("id");
            } else return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public Courier findCourierNameByOrderNumber(int orderNumber) {
        try {
            PreparedStatement courierStatement =
                    connection.prepareStatement("select * from `order_courier` where order_number=?");
            courierStatement.setInt(1, orderNumber);

            ResultSet resultSet = courierStatement.executeQuery();
            if (resultSet.next()) {
                int courier_id = resultSet.getInt("courier_id");
                courierStatement =
                        connection.prepareStatement("select * from `courier` where id=?");
                courierStatement.setInt(1, courier_id);

                ResultSet courierData = courierStatement.executeQuery();
                if (courierData.next()) {
                    String surname = courierData.getString("surname");
                    String name = courierData.getString("name");
                    return new Courier(0, surname, name, null, null);
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Courier findCourier(String employeeNumber) {
        try {
            PreparedStatement courierStatement =
                    connection.prepareStatement("select * from `courier` where employee_number=?");
            courierStatement.setString(1, employeeNumber);

            int courierId = 0;
            ResultSet courierSet = courierStatement.executeQuery();
            Courier courier;
            if (courierSet.next()) {
                courierId = courierSet.getInt("id");
                courier = new Courier(courierId, courierSet.getString("surname"),
                        courierSet.getString("name"), employeeNumber, null);
            } else return null;

            courierStatement = connection.prepareStatement("select * from `order_courier` where courier_id=?");
            courierStatement.setInt(1, courierId);
            courierSet = courierStatement.executeQuery();

            List<DeliveryOrder> couriersOrders = new ArrayList<>();
            while (courierSet.next()) {
                couriersOrders.add(new UpdatedOrderDaoImplementation(databasePath, databaseUsername,
                        databasePassword, connection).findOrderByOrderNumber(courierSet.getInt("order_number")));
            }

            UpdatedUserDao userDao = new UpdatedUserDaoImplementation(databasePath, databaseUsername,
                    databasePassword, connection);

            courier.setOrders(couriersOrders);
            for (DeliveryOrder order : couriersOrders) {
                order.setCourier(courier);
                order.setUser(userDao.getUserNameByOrderNumber(order.getOrderNumber()));
            }

            return courier;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<DeliveryOrder> getCourierOrders(String employeeNumber) {
        return findCourier(employeeNumber).getOrders();
    }
}
