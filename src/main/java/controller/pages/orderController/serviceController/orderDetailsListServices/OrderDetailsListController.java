package controller.pages.orderController.serviceController.orderDetailsListServices;

import db.DBConnection;
import model.OrderDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsListController implements OrderDetailsListControllerService {

    @Override
    public List<OrderDetails> getOrderDetailsList() {
        return getAllOrderDetailsData();
    }

    private List<OrderDetails> getAllOrderDetailsData() {
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


}
