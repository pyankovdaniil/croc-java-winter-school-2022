package ru.croc.task18.dao;

import ru.croc.task18.exceptions.IllegalProductVendorCodeException;
import ru.croc.task18.exceptions.NoSuchProductException;
import ru.croc.task18.shopelements.Product;

import java.sql.*;

public class ProductDaoImplementation implements ProductDao {
    private final String databasePath;
    private final String databaseUsername;
    private final String databasePassword;

    public ProductDaoImplementation(String databasePath, String databaseUsername, String databasePassword) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    @Override
    public Product findProductById(int productId) {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
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
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
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
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            if (findProduct(product.getVendorCode()) != null) {
                throw new IllegalProductVendorCodeException("Already have product with vendor code: "
                        + product.getVendorCode());
            } else {
                PreparedStatement addProductStatement = connection.prepareStatement(
                        "insert into `product` (vendor_code, name, price) values (?, ?, ?)");
                addProductStatement.setString(1, product.getVendorCode());
                addProductStatement.setString(2, product.getName());
                addProductStatement.setInt(3, product.getPrice());
                addProductStatement.execute();
            }

            return findProduct(product.getVendorCode());
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Product updateProduct(Product product) throws NoSuchProductException {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            if (findProduct(product.getVendorCode()) == null) {
                throw new NoSuchProductException("Can not find a product with vendor code " + product.getVendorCode()
                        + " in database");
            }

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
    public void deleteProduct(String vendorCode) throws NoSuchProductException {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            Product product = findProduct(vendorCode);
            if (product == null) {
                throw new NoSuchProductException("Can not find a product with vendor code " + vendorCode
                        + " in database");
            }

            PreparedStatement statement = connection.prepareStatement("delete from `order` where product_id=?");
            statement.setInt(1, product.getId());
            statement.execute();

            statement = connection.prepareStatement("delete from `product` where id=?");
            statement.setInt(1, product.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
