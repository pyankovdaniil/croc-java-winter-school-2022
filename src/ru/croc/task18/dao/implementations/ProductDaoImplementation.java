package ru.croc.task18.dao.implementations;

import ru.croc.task18.dao.ProductDao;
import ru.croc.task18.exceptions.IllegalProductVendorCodeException;
import ru.croc.task18.shopelements.Product;

import java.sql.*;

public class ProductDaoImplementation implements ProductDao {
    private final String databasePath;
    private final String databaseUsername;
    private final String databasePassword;
    private final Connection connection;

    public ProductDaoImplementation(String databasePath, String databaseUsername, String databasePassword,
                                    Connection connection) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.connection = connection;
    }

    @Override
    public Product findProductById(int productId) {
        try {
            PreparedStatement productStatement =
                    connection.prepareStatement("select * from `product` where id=?");
            productStatement.setInt(1, productId);

            ResultSet productSet = productStatement.executeQuery();
            if (productSet.next()) {
                return new Product(productSet.getInt("id"), productSet.getString("vendor_code"),
                        productSet.getString("name"), productSet.getInt("price"));
            } else return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Product findProduct(String vendorCode) {
        try {
            PreparedStatement productStatement =
                    connection.prepareStatement("select * from `product` where vendor_code=?");
            productStatement.setString(1, vendorCode);

            ResultSet productSet = productStatement.executeQuery();
            if (productSet.next()) {
                return new Product(productSet.getInt("id"), productSet.getString("vendor_code"),
                        productSet.getString("name"), productSet.getInt("price"));
            } else return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Product createProduct(Product product) throws IllegalProductVendorCodeException {
        try {
            PreparedStatement addProductStatement = connection.prepareStatement(
                    "insert into `product` (vendor_code, name, price) values (?, ?, ?)");
            addProductStatement.setString(1, product.getVendorCode());
            addProductStatement.setString(2, product.getName());
            addProductStatement.setInt(3, product.getPrice());
            addProductStatement.execute();

            return findProduct(product.getVendorCode());
        } catch (SQLException e) {
            throw new IllegalProductVendorCodeException("Already have a product with vendor code \"" +
                    product.getVendorCode() + "\"");
        }
    }

    @Override
    public Product updateProduct(Product product) {
        try {
            PreparedStatement productStatement =
                    connection.prepareStatement("update `product` set name=?, price=? where vendor_code=?");
            productStatement.setString(1, product.getName());
            productStatement.setInt(2, product.getPrice());
            productStatement.setString(3, product.getVendorCode());
            productStatement.execute();


            return findProduct(product.getVendorCode());
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void deleteProduct(String vendorCode) {
        try {
            Product product = findProduct(vendorCode);
            PreparedStatement statement = connection.prepareStatement("delete from `order_product` " +
                    "where product_id=?");
            statement.setInt(1, product.getId());
            statement.execute();

            statement = connection.prepareStatement("delete from `product` where id=?");
            statement.setInt(1, product.getId());
            statement.execute();
        } catch (Exception ignored) {
        }
    }
}
