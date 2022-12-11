package ru.croc.task19.dao;

import ru.croc.task18.dao.UserDao;
import ru.croc.task18.shopelements.User;
import ru.croc.task19.updatedshopelements.DeliveryOrder;

import java.util.List;

public interface UpdatedUserDao extends UserDao {
    User getUserNameByOrderNumber(int orderNumber);
    List<DeliveryOrder> getUserOrders(String userName);
}
