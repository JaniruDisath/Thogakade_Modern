package controller.pages.customerController.services;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerServices {
    @Override
    public List<String> getCustomerIds() {
        return getAllCustomerIds();
    }

    public List<String> getAllCustomerIds() {
        List<String> customerIDList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("Select * FROM customer;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customerIDList.add(resultSet.getString("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerIDList;
    }
}
