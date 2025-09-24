package repository.OrdersTable;

import db.DBConnection;
import model.Orders;
import repository.Thogakade_Modern;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersTable implements Thogakade_Modern<Orders> {
    @Override
    public List<Orders> getAllData() {
        List<Orders> itemDetails = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("Select * FROM orders;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                itemDetails.add(new Orders(
                                resultSet.getString("orderID"),
                                resultSet.getDate("orderDate").toLocalDate(),
                                resultSet.getString("custID")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemDetails;
    }

    @Override
    public List<String> getListOfPrimaryKeys() {
        return List.of();
    }


    //Insert An Item
    @Override
    public void insertAnItem(Orders orders) {

    }


    //Delete An Item
    @Override
    public void deleteAnItem(String primaryID) {

    }

    //Update An Item
    @Override
    public void updateAnItem(Orders orders) {

    }


}
