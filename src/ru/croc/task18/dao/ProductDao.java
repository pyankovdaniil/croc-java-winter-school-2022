package ru.croc.task18.dao;

import ru.croc.task18.exceptions.IllegalProductVendorCodeException;
import ru.croc.task18.exceptions.NoSuchProductException;
import ru.croc.task18.shopelements.Product;

public interface ProductDao {
    Product findProduct(String vendorCode);
    Product createProduct(Product product) throws IllegalProductVendorCodeException;
    Product updateProduct(Product product) throws NoSuchProductException;
    void deleteProduct(String vendorCode) throws NoSuchProductException;
}
