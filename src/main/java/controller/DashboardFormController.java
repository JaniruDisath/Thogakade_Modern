package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    private Stage customerDetails = new Stage();
    private Stage itemDetails = new Stage();

    @FXML
    private Button customerDetailsButton;

    @FXML
    private Button itemDetailsButton;

    @FXML
    void onCustomerButton(ActionEvent event) {
        try {
            customerDetails.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerDetails.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        customerDetails.setResizable(false);
        customerDetails.show();
    }

    @FXML
    void onItemDetails(ActionEvent event) {
        try {
            itemDetails.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemDetails.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemDetails.setResizable(false);
        itemDetails.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDatabase();
    }

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "thogakade_modern";  // your database name
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // Get connection (without DB first)
    private Connection getServerConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Get connection with DB
    private Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
    }

    public void initializeDatabase() {
        try (Connection conn = getServerConnection();
             Statement stmt = conn.createStatement()) {

            // 1. Create database if not exists
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

            // 2. Connect to the new/existing database
            try (Connection dbConn = getDatabaseConnection();
                 Statement dbStmt = dbConn.createStatement()) {

                // 3. Create tables if not exist
                String createCustomerTable = """
                        CREATE TABLE IF NOT EXISTS customer (
                            id VARCHAR(20) PRIMARY KEY,
                            title VARCHAR(10) NOT NULL,
                            name VARCHAR(100) NOT NULL,
                            dob DATE NOT NULL,
                            salary DOUBLE NOT NULL,
                            address VARCHAR(200),
                            city VARCHAR(50),
                            province VARCHAR(50),
                            postalCode VARCHAR(10)
                        )
                        """;

                String createItemTable = """
                        CREATE TABLE IF NOT EXISTS item (
                            itemCode VARCHAR(20) PRIMARY KEY,
                            description VARCHAR(200) NOT NULL,
                            packSize VARCHAR(20),
                            unitPrice DOUBLE NOT NULL,
                            qtyOnHand INT NOT NULL
                        )
                        """;


                dbStmt.executeUpdate(createCustomerTable);
                dbStmt.executeUpdate(createItemTable);

                // 4. Insert sample data only if table is empty
                ResultSet rs1 = dbStmt.executeQuery("SELECT COUNT(*) FROM customer");
                rs1.next();
                if (rs1.getInt(1) == 0) {
                    dbStmt.executeUpdate("""
                                INSERT INTO customer (id, title, name, dob, salary, address, city, province, postalCode) VALUES
                                ('C001', 'Mr', 'John Doe', '1990-05-12', 55000.00, '123 Main St', 'Colombo', 'Western', '10001'),
                                ('C002', 'Ms', 'Jane Smith', '1985-03-22', 72000.00, '45 Lake Rd', 'Kandy', 'Central', '20000'),
                                ('C003', 'Mrs', 'Emily Johnson', '1992-11-10', 61000.00, '89 Temple Ln', 'Galle', 'Southern', '80000'),
                                ('C004', 'Mr', 'Michael Brown', '1988-07-19', 83000.00, '12 Park Ave', 'Jaffna', 'Northern', '40000'),
                                ('C005', 'Miss', 'Sophia Wilson', '1995-01-05', 45000.00, '78 Hill St', 'Matara', 'Southern', '81000'),
                                ('C006', 'Dr', 'Daniel White', '1979-09-14', 120000.00, '56 Green Way', 'Negombo', 'Western', '11000'),
                                ('C007', 'Mr', 'David Lee', '1993-02-28', 53000.00, '22 Palm Grove', 'Kurunegala', 'North Western', '60000'),
                                ('C008', 'Ms', 'Olivia Taylor', '1987-12-01', 70000.00, '90 Rose St', 'Anuradhapura', 'North Central', '50000'),
                                ('C009', 'Mr', 'James Martin', '1991-06-30', 67000.00, '11 Lotus Rd', 'Badulla', 'Uva', '90000'),
                                ('C010', 'Mrs', 'Isabella Clark', '1984-08-16', 95000.00, '34 River Bank', 'Ratnapura', 'Sabaragamuwa', '70000')
                            """);
                }

                ResultSet rs2 = dbStmt.executeQuery("SELECT COUNT(*) FROM item");
                rs2.next();
                if (rs2.getInt(1) == 0) {

                    dbStmt.executeUpdate("""
                            INSERT INTO item (itemCode, description, packSize, unitPrice, qtyOnHand) VALUES
                            ('I001', 'Cheeseburger', 'Single', 5.99, 50),
                            ('I002', 'Veggie Burger', 'Single', 5.49, 40),
                            ('I003', 'Double Beef Burger', 'Double', 8.99, 30),
                            ('I004', 'Chicken Burger', 'Single', 6.49, 45),
                            ('I005', 'Fish Burger', 'Single', 6.79, 25),
                            ('I006', 'Spicy Chicken Burger', 'Single', 6.99, 35),
                            ('I007', 'Mushroom Swiss Burger', 'Single', 7.29, 20),
                            ('I008', 'Bacon BBQ Burger', 'Single', 7.49, 18),
                            ('I009', 'Grilled Chicken Burger', 'Single', 6.59, 27),
                            ('I010', 'Paneer Burger', 'Single', 6.19, 22)
                            """);
                }

                System.out.println("Database and tables initialized successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
