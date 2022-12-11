package ru.croc.task19.updatedshopelements;

import java.util.ArrayList;
import java.util.List;

public class Courier {
    private int id;
    private String surname;
    private String name;
    private String employeeNumber;
    private List<DeliveryOrder> orders;

    public Courier(int id, String surname, String name, String employeeNumber, List<DeliveryOrder> orders) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.orders = orders;
    }

    public Courier(Courier courier) {
        this(courier.id, courier.surname, courier.name, courier.employeeNumber, courier.getOrders());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public List<DeliveryOrder> getOrders() {
        return new ArrayList<>(orders);
    }

    public void setOrders(List<DeliveryOrder> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Courier{id = ").append(id).append(", surname = ")
                .append(surname).append(", name = ").append(name).append(", employeeNumber = ")
                .append(employeeNumber).append(", orders = {\n");

        for (DeliveryOrder order : orders) {
            stringBuilder.append("\tOrder #").append(order.getOrderNumber()).append(" : ").append(order.getProducts())
                    .append("\n");
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
