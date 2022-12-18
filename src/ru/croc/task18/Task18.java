package ru.croc.task18;

import ru.croc.task17.database.DatabasePrinter;
import ru.croc.task17.database.implementations.ShopDatabasePrinter;
import ru.croc.task18.dao.OrderDao;
import ru.croc.task18.dao.ProductDao;
import ru.croc.task18.dao.implementations.OrderDaoImplementation;
import ru.croc.task18.dao.implementations.ProductDaoImplementation;
import ru.croc.task18.exceptions.IllegalProductVendorCodeException;
import ru.croc.task18.exceptions.NoSuchUserException;
import ru.croc.task18.shopelements.Order;
import ru.croc.task18.shopelements.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Task18 {
    public static void main(String[] args) throws SQLException {
        String databasePath = "jdbc:h2:/DatabaseFiles/H2/shop_db";
        String databaseUsername = "sa";
        String databasePassword = "";

        Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword);
        ProductDao productDao = new ProductDaoImplementation(databasePath, databaseUsername, databasePassword,
                connection);

        System.out.println("--- Test findProduct() ---");
        System.out.println(productDao.findProduct("T1"));
        System.out.println(productDao.findProduct("T3"));
        System.out.println(productDao.findProduct("T5") + "\n");


        System.out.println("--- Test createProduct() ---");
        System.out.println("- Database before: -");

        DatabasePrinter databasePrinter = new ShopDatabasePrinter(connection);
        databasePrinter.printDatabase(databasePath, databaseUsername, databasePassword);

        try {
            Product productToSave = new Product(0, "T6", "Sony Playstation 4 Pro", 30000);
            Product savedProduct = productDao.createProduct(productToSave);
            System.out.println("\nProduct " + productToSave.getName() + " was saved. Its id = " + savedProduct.getId());

            Product existingProduct = new Product(0, "T1", "Some name", 100000);
            productDao.createProduct(existingProduct);
        } catch (IllegalProductVendorCodeException e) {
            System.out.println("\n" + e.getMessage());
        }

        System.out.println("\n- Database after: -");
        databasePrinter.printDatabase(databasePath, databaseUsername, databasePassword);
        System.out.println();


        System.out.println("--- Test updateProduct() ---\n");
        System.out.println("- Database before updating -");
        databasePrinter.printDatabase(databasePath, databaseUsername, databasePassword);

        Product updatedProduct = new Product(0, "T6", "Xbox Series S", 60000);
        updatedProduct = productDao.updateProduct(updatedProduct);

        if (updatedProduct != null) {
            System.out.println("\nProduct " + updatedProduct.getName() + " was updated. Its id = "
                    + updatedProduct.getId());
        }

        System.out.println("\n- Database after updating -");
        databasePrinter.printDatabase(databasePath, databaseUsername, databasePassword);


        System.out.println("\n--- Test deleteProduct() ---");
        System.out.println("- Database before deleting -");
        databasePrinter.printDatabase(databasePath, databaseUsername, databasePassword);

        String vendorCodeToDelete = "T3";
        productDao.deleteProduct(vendorCodeToDelete);

        System.out.println("- Database after deleting -");
        databasePrinter.printDatabase(databasePath, databaseUsername, databasePassword);


        System.out.println("\n--- Test createOrder() ---");
        System.out.println("- Database before creating order -");
        databasePrinter.printDatabase(databasePath, databaseUsername, databasePassword);

        OrderDao orderDao = new OrderDaoImplementation(databasePath, databaseUsername, databasePassword,
                connection);

        List<Product> productList = new ArrayList<>();
        productList.add(new Product(0, "T6", null, 0));
        productList.add(new Product(0, "T5", null, 0));

        try {
            Order order = orderDao.createOrder("vasya", productList);
            System.out.println("\n" + order + "\n");
        } catch (NoSuchUserException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("- Database after creating order -");
        databasePrinter.printDatabase(databasePath, databaseUsername, databasePassword);

        connection.close();
    }
}
