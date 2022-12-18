package ru.croc.task18.dao;

import ru.croc.task18.exceptions.IllegalProductVendorCodeException;
import ru.croc.task18.shopelements.Product;

public interface ProductDao {
    Product findProductById(int productId);
    Product findProduct(String vendorCode);
    Product createProduct(Product product) throws IllegalProductVendorCodeException;
    Product updateProduct(Product product);
    void deleteProduct(String vendorCode);
}
