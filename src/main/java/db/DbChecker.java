package db;

import util.DbConfig;
import util.SqlScripts;

import java.sql.*;

public class DbChecker {
    private final String URL = DbConfig.URL;
    private final String DB_NAME = DbConfig.DB_NAME;  // your database name
    private final String USER = DbConfig.USER;
    private final String PASSWORD = DbConfig.PASSWORD;

    // Get connection (without DB first)
    private Connection getServerConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Get connection with DB
    private Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
    }

    public void initializeDatabase() {
        try (Connection conn = DbLogin.getServerConnection();
             Statement stmt = conn.createStatement()) {

            // Create database if not exists
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

            // Connect to the new or existing database
            try (Connection dbConn = DbLogin.getDatabaseConnection();
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
