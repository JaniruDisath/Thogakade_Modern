package handlers.pages;

import controller.pages.orderController.serviceController.OrderController;
import controller.pages.orderController.serviceController.OrderControllerService;
import handlers.IHandler;
import model.Item;

public class OrderControllerHandler implements IHandler {

    private OrderControllerService controllerService;

    public OrderControllerHandler(OrderControllerService controllerService){
        this.controllerService=controllerService;
    }
    @Override
    public void onOrderCardClick(String orderID) {
        controllerService.setOrderDetailsListUI(orderID);
    }

    @Override
    public void itemCardAddItem(Item item, Integer count) {

    }

    @Override
    public void basketItemRemove(Item item) {

    }
}
