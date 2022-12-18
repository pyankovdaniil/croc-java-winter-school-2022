package ru.croc.task19.dao.implementations;

import ru.croc.task18.dao.OrderDao;
import ru.croc.task18.dao.implementations.OrderDaoImplementation;
import ru.croc.task19.dao.UpdatedOrderDao;
import ru.croc.task19.updatedshopelements.DeliveryOrder;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UpdatedOrderDaoImplementation implements UpdatedOrderDao {
    private final String databasePath;
    private final String databaseUsername;
    private final String databasePassword;
    private final Connection connection;

    public UpdatedOrderDaoImplementation(String databasePath, String databaseUsername,
                                         String databasePassword, Connection connection) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.connection = connection;
    }

    @Override
    public DeliveryOrder findOrderByOrderNumber(int orderNumber) {
        try {
            PreparedStatement orderStatement =
                    connection.prepareStatement("select * from `order` where order_number=?");
            orderStatement.setInt(1, orderNumber);
            ResultSet orderSet = orderStatement.executeQuery();

            if (orderSet.next()) {
                OrderDao orderDao = new OrderDaoImplementation(databasePath, databaseUsername,
                        databasePassword, connection);
                String deliveryTimeString = orderSet.getString("delivery_date");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm");

                return new DeliveryOrder(orderNumber, null, orderDao.findOrderProducts(orderNumber),
                        simpleDateFormat.parse(deliveryTimeString), null);
            } else return null;
        } catch (SQLException | ParseException e) {
            return null;
        }
    }
}
