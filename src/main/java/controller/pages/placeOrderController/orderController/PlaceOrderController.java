package controller.pages.placeOrderController.orderController;

import controller.pages.placeOrderController.formController.PlaceOrderFormController;
import controller.pages.placeOrderController.formController.PlaceOrderFormControllerService;
import controller.pages.placeOrderController.orderController.basketItemService.BasketItemController;
import controller.pages.placeOrderController.orderController.basketItemService.BasketItemControllerService;
import services.interfaceElements.stockItems.StockItemsController;
import services.interfaceElements.stockItems.StockItemsControllerService;
import handlers.IHandler;
import handlers.pages.PlaceOrderControllerHandler;
import javafx.scene.layout.HBox;
import model.Item;
import model.dto.cart.CartItems;
import model.dto.cart.CartListVBox;

import java.util.List;


public class PlaceOrderController implements PlaceOrderControllerService {

    private PlaceOrderFormControllerService ui ;
    //Handler - UI Handler
    private IHandler handler = new PlaceOrderControllerHandler(this);
    //Services - Interface Elements - Stock Items Service
    private final StockItemsControllerService stockItemsControllerService;
    private final BasketItemControllerService basketItemControllerService;




    public PlaceOrderController(PlaceOrderFormControllerService ui) {
        this.ui = ui;
        this.handler = new PlaceOrderControllerHandler(this);
        this.stockItemsControllerService = new StockItemsController(handler);
        this.basketItemControllerService = new BasketItemController(handler);

    }


    //Return the grocery items
    @Override
    public List<HBox> getItemData(PlaceOrderFormController controller) {
        return stockItemsControllerService.getItemData(controller);

    }


    //Basket Items
    @Override
    public void addCartListElement(Item item, Integer count) {
        basketItemControllerService.addCartListElement(item,count);
        ui.updateCartUIValues();
//        setBasketItemController();
    }

//    public void setBasketItemController(){
//        for (int i = 0; i <getCartListElements().size() ; i++) {
//            getCartListElements().get(i).getControllerService();
//        }
//    }

    @Override
    public List<CartListVBox> getCartListElements() {
        return basketItemControllerService.getCartListElements();
    }

    @Override
    public double getTotalInTheCart() {
        return basketItemControllerService.getTotalInTheCart();
    }

    @Override
    public String getTotalString() {
        return basketItemControllerService.getTotalString();
    }

    @Override
    public List<CartItems> getCartItems() {
        return basketItemControllerService.getCartItems();
    }

    @Override
    public void resetList() {
        basketItemControllerService.resetList();
        stockItemsControllerService.clearUIItemList();
    }

    @Override
    public void removeAnItem(Item item) {
        basketItemControllerService.removeAnItem(item);
        ui.updateCartUIValues();
    }

}

