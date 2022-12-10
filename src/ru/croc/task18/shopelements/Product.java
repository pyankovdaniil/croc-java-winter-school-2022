package ru.croc.task18.shopelements;

public class Product {
    private int id;
    private String vendorCode;
    private String name;
    private int price;

    public Product(int id, String vendorCode, String name, int price) {
        this.id = id;
        this.vendorCode = vendorCode;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", vendorCode='" + vendorCode + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
