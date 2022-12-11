package ru.croc.task19.dao;

import ru.croc.task19.updatedshopelements.Courier;
import ru.croc.task19.updatedshopelements.DeliveryOrder;

import java.util.List;

public interface CourierDao {
    int findCourierIdByEmployeeNumber(String employeeNumber);
    Courier findCourierNameByOrderNumber(int orderNumber);
    Courier findCourier(String employeeNumber);
    List<DeliveryOrder> getCourierOrders(String employeeNumber);
}
