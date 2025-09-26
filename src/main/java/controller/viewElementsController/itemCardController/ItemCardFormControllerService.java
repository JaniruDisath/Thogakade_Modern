package controller.viewElementsController.itemCardController;

import controller.pages.placeOrderController.orderController.PlaceOrderControllerService;
import handlers.IHandler;
import model.Item;

public interface ItemCardFormControllerService {
    void setData(Item item, IHandler handler);

    void changeCount(Integer count);
}
