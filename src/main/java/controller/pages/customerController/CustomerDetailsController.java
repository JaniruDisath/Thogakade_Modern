package controller.pages.customerController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.*;

public class CustomerDetailsController implements CustomerDetailsService {

    @Override
    public void addCustomerDetails(Customer customer) {

        String SQL = "INSERT INTO customer(id, title, name, dob, salary, address, city, province, postalCode) VALUES(?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(SQL);

            preparedStatement.setObject(1, customer.getId());
            preparedStatement.setObject(2, customer.getTitle());
            preparedStatement.setObject(3, customer.getName());
            preparedStatement.setObject(4, customer.getDob());
            preparedStatement.setObject(5, customer.getSalary());
            preparedStatement.setObject(6, customer.getAddress());
            preparedStatement.setObject(7, customer.getCity());
            preparedStatement.setObject(8, customer.getProvince());
            preparedStatement.setObject(9, customer.getPostalCode());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCustomerDetails(String customerId) {
        String SQL = "delete from customer where id='" + customerId + "'";

        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(SQL);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCustomerDetails(Customer customer) {
        String SQL = """
                UPDATE customer
                SET title = ?, name = ?, dob = ?, salary = ?, address = ?, city = ?, province = ?, postalCode = ?
                WHERE id = ?;
                """;
        try (PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(SQL)) {

            preparedStatement.setObject(1, customer.getTitle());
            preparedStatement.setObject(2, customer.getName());
            preparedStatement.setObject(3, customer.getDob());   // LocalDate will auto-map if using MySQL 8+ driver
            preparedStatement.setObject(4, customer.getSalary());
            preparedStatement.setObject(5, customer.getAddress());
            preparedStatement.setObject(6, customer.getCity());
            preparedStatement.setObject(7, customer.getProvince());
            preparedStatement.setObject(8, customer.getPostalCode());
            preparedStatement.setObject(9, customer.getId());    // WHERE clause

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Customer updated successfully!");
            } else {
                System.out.println("No customer found with id: " + customer.getId());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Customer> getAllCustomerDetails() {
        ObservableList<Customer> itemDetails = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("Select * FROM customer;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                itemDetails.add(new Customer(
                                resultSet.getString("id"),
                                resultSet.getString("title"),
                                resultSet.getString("name"),
                                resultSet.getDate("dob").toLocalDate(),
                                resultSet.getDouble("salary"),
                                resultSet.getString("address"),
                                resultSet.getString("city"),
                                resultSet.getString("province"),
                                resultSet.getString("postalCode")
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemDetails;
    }

}
