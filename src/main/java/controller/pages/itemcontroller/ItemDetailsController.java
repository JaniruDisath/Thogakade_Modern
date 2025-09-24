package controller.pages.itemcontroller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;

import java.sql.*;

public class ItemDetailsController implements ItemDetailsService {

    @Override
    public void addItemDetails(Item item) {
        String SQL = "INSERT INTO item(itemCode, description, packSize, unitPrice, qtyOnHand) VALUES(?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(SQL);

            preparedStatement.setObject(1, item.getItemCode());
            preparedStatement.setObject(2, item.getDescription());
            preparedStatement.setObject(3, item.getPackSize());
            preparedStatement.setObject(4, item.getUnitPrice());
            preparedStatement.setObject(5, item.getQtyOnHand());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteItemDetails(String itemId) {
        String SQL = "delete from item where itemCode='" + itemId + "'";
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(SQL);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItemDetails(Item item) {
        String SQL = """
                UPDATE item
                SET  description = ?, packSize = ?, unitPrice = ?, qtyOnHand = ?
                WHERE itemCode = ?;
                """;

        try (PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(SQL)) {

            preparedStatement.setObject(1, item.getDescription());
            preparedStatement.setObject(2, item.getPackSize());
            preparedStatement.setObject(3, item.getUnitPrice());   // LocalDate will auto-map if using MySQL 8+ driver
            preparedStatement.setObject(4, item.getQtyOnHand());
            preparedStatement.setObject(5, item.getItemCode()); // WHERE clause

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Customer updated successfully!");
            } else {
                System.out.println("No customer found with id: " + item.getItemCode());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Item> getAllCustomerDetails() {
        ObservableList<Item> itemDetails = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("Select * FROM item;");
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
