package repository.OrderDetailTable;

import db.DBConnection;
import model.OrderDetails;
import repository.Thogakade_Modern;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailTable implements Thogakade_Modern<OrderDetails> {

    @Override
    public List<OrderDetails> getAllData() {
        List<OrderDetails> itemDetails = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("Select * FROM orderdetail");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                itemDetails.add(new OrderDetails(
                                resultSet.getString("orderID"),
                                resultSet.getString("itemCode"),
                                resultSet.getInt("orderQty"),
                                resultSet.getInt("discount")
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
    public void insertAnItem(OrderDetails orderDetails) {

    }


    //Delete An Item
    @Override
    public void deleteAnItem(String primaryID) {

    }

    //Update An Item
    @Override
    public void updateAnItem(OrderDetails orderDetails) {

    }
}
