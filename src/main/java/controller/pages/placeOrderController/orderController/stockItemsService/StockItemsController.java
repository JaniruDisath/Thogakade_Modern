package controller.pages.placeOrderController.orderController.stockItemsService;

import controller.pages.placeOrderController.formController.PlaceOrderFormController;
import controller.pages.placeOrderController.orderController.PlaceOrderControllerService;
import controller.viewElementsController.itemCardController.ItemCardFormController;
import db.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Item;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockItemsController implements StockItemsControllerService {

    private final PlaceOrderControllerService placeOrderControllerService;

    public StockItemsController(PlaceOrderControllerService placeOrderControllerService){
        this.placeOrderControllerService=placeOrderControllerService;
    }

    //Items Row
    private HBox currentRow;
    //All the itesm finalized
    private List<HBox> finalColumn = new ArrayList<>();
    //Card Counter
    private int cardCount = 0;
    //Card per row
    private final int MAX_CARDS_PER_ROW = 5;

    //Getting Grocery Elements on to the Dashboard
    @Override
    public List<HBox> getItemData(PlaceOrderFormController controller) {
        finalColumn.clear();
        loadItemData();
        return finalColumn;
    }

    //Getting Items and loading them to the UI
    private void loadItemData() {
        List<Item> itemDetails = getAllCustomerDetails();
        for (Item itemDetail : itemDetails) {
            addItemsRowByRow(itemDetail);
        }
    }

    //Preparing UI Card and setting data
    private AnchorPane getUICard(Item item) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/elements/ItemCard.fxml"));
        AnchorPane itemCard;
        try {
            itemCard = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        ItemCardFormController controller = loader.getController();
        controller.setData(item, placeOrderControllerService);
        return itemCard;
    }

    //Separately add Item cards separating row by row
    private void addItemsRowByRow(Item item) {
        AnchorPane itemCard = getUICard(item);
        if (currentRow == null || cardCount >= MAX_CARDS_PER_ROW) {
            currentRow = new HBox(10);
            finalColumn.add(currentRow);
            cardCount = 0;
        }
        currentRow.getChildren().add(itemCard);
        cardCount++;
    }

    //Getting data from the Database
    public List<Item> getAllCustomerDetails() {
        List<Item> itemDetails = new ArrayList<>();
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
        }finally {

        }
        return itemDetails;
    }

    @Override
    public void clearUIItemList(){
        finalColumn.clear();
    }

}
