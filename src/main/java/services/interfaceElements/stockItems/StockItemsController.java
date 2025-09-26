package services.interfaceElements.stockItems;

import controller.pages.placeOrderController.formController.PlaceOrderFormController;
import controller.viewElementsController.itemCardController.ItemCardFormControllerService;
import handlers.IHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Item;
import repository.ItemTable.ItemTable;
import repository.Thogakade_Modern;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockItemsController implements StockItemsControllerService {

    private final IHandler handler;

    public StockItemsController(IHandler handler){
        this.handler=handler;
    }

    //Items Row
    private HBox currentRow;
    //All the items finalized
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
        ItemCardFormControllerService controller = loader.getController();
        controller.setData(item, handler);
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
        Thogakade_Modern<Item> thogakade_modern = new ItemTable();
        return thogakade_modern.getAllData();
    }

    //clear the element List
    @Override
    public void clearUIItemList(){
        finalColumn.clear();
    }

}
