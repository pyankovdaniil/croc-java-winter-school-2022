package ru.croc.task19.dao;

import ru.croc.task19.updatedshopelements.DeliveryOrder;

public interface UpdatedOrderDao {
    DeliveryOrder findOrderByOrderNumber(int orderNumber);
}
