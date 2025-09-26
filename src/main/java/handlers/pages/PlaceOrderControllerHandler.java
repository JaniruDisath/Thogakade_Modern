package handlers.pages;

import controller.pages.placeOrderController.orderController.PlaceOrderControllerService;
import handlers.IHandler;
import model.Item;

public class PlaceOrderControllerHandler implements IHandler  {

    private PlaceOrderControllerService service;

    public PlaceOrderControllerHandler(PlaceOrderControllerService service){
        this.service=service;
    }

    @Override
    public void onOrderCardClick(String orderID) {

    }

    @Override
    public void itemCardAddItem(Item item, Integer count) {
        service.addCartListElement(item,count);
    }

    @Override
    public void basketItemRemove(Item item) {
        service.removeAnItem(item);
    }
}
