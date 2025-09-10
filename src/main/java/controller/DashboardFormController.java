package controller;

import controller.util.DbConfig;
import controller.util.SqlScripts;
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

    private static final String URL = DbConfig.URL;
    private static final String DB_NAME = DbConfig.DB_NAME;  // your database name
    private static final String USER = DbConfig.USER;
    private static final String PASSWORD = DbConfig.PASSWORD;

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

                // Create tables if not exist

                dbStmt.executeUpdate(SqlScripts.CREATE_CUSTOMER_TABLE);
                dbStmt.executeUpdate(SqlScripts.CREATE_ITEM_TABLE);
                dbStmt.executeUpdate(SqlScripts.CREATE_ORDERS_TABLE);
                dbStmt.executeUpdate(SqlScripts.CREATE_ORDER_DETAIL_TABLE);

                //Insert sample data only if table is empty
                ResultSet rs1 = dbStmt.executeQuery("SELECT COUNT(*) FROM customer");
                rs1.next();
                if (rs1.getInt(1) == 0) {
                    dbStmt.executeUpdate(SqlScripts.INSET_INTO_CUSTOMER);
                }

                ResultSet rs2 = dbStmt.executeQuery("SELECT COUNT(*) FROM item");
                rs2.next();
                if (rs2.getInt(1) == 0) {

                    dbStmt.executeUpdate(SqlScripts.INSET_INTO_ITEM);
                }

                ResultSet rs3 = dbStmt.executeQuery("SELECT COUNT(*) FROM orders");
                rs3.next();
                if (rs3.getInt(1) == 0) {

                    dbStmt.executeUpdate(SqlScripts.INSET_INTO_ORDERS);
                }

                ResultSet rs4 = dbStmt.executeQuery("SELECT COUNT(*) FROM orderDetail");
                rs4.next();
                if (rs4.getInt(1) == 0) {

                    dbStmt.executeUpdate(SqlScripts.INSET_INTO_ORDER_DETAIL);
                }

                System.out.println("Database and tables initialized successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
