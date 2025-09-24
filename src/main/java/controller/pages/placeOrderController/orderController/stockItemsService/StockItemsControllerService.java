package controller.pages.placeOrderController.orderController.stockItemsService;

import controller.pages.placeOrderController.formController.PlaceOrderFormController;
import javafx.scene.layout.HBox;

import java.util.List;

public interface StockItemsControllerService {

    List<HBox> getItemData(PlaceOrderFormController controller);
    void clearUIItemList();
}
