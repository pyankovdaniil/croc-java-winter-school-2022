package ru.croc.task19.updatedshopelements;

import ru.croc.task18.shopelements.Order;
import ru.croc.task18.shopelements.Product;
import ru.croc.task18.shopelements.User;

import java.util.Date;
import java.util.List;

public class DeliveryOrder extends Order {
    private Date deliveryTime;
    private Courier courier;

    public DeliveryOrder(int orderId, User user, List<Product> products, Date deliveryTime, Courier courier) {
        super(orderId, user, products);
        this.deliveryTime = deliveryTime;
        this.courier = courier;
    }

    public Courier getCourier() {
        return new Courier(courier);
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\n" + getUser().getUserName() + "'s order #"
                + getOrderNumber() + ": {\n");
        for (Product product : getProducts()) {
            stringBuilder.append("\t").append(product.getVendorCode()).append(", ").append(product.getName())
                    .append(", ").append(product.getPrice()).append("\n");
        }
        return stringBuilder.append("}\nDelivery time: ").append(deliveryTime).append(", courier: ")
                .append(courier.getSurname()).append(" ").append(courier.getName()).toString();
    }
}
