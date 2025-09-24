package controller.pages.orderController.serviceController.stockItemsListService;

import db.DBConnection;
import model.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockItemsListController implements StockItemsListInterface {


    @Override
    public List<Item> getItemsList() {
        return getAllItemsList();
    }

    private List<Item> getAllItemsList() {
        List<Item> itemDetails = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("Select * FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                itemDetails.add(new Item(
                                resultSet.getString("itemCode"),
                                resultSet.getString("description"),
                                resultSet.getString("packSize"),
                                resultSet.getDouble("unitPrice"),
                                resultSet.getInt("qtyOnHand"),
                                resultSet.getString("itemImage")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemDetails;
    }

}
