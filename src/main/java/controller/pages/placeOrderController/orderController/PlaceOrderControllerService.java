package controller.pages.placeOrderController.orderController;

import controller.pages.placeOrderController.formController.PlaceOrderFormController;
import javafx.scene.layout.HBox;
import model.Item;
import model.dto.cart.CartItems;
import model.dto.cart.CartListVBox;

import java.util.List;

public interface PlaceOrderControllerService {
    //To get the Item list set for the dashboard
    List<HBox> getItemData(PlaceOrderFormController controller);
    //To add an item for the cart
    void addCartListElement(Item item, Integer count);
    //To get elements for the cart
    List<CartListVBox> getCartListElements();
    //To get the total amount in the cart
    double getTotalInTheCart();
    //To get total amount as a String
    String getTotalString();
    //To get the Items And Count in the basket
    List<CartItems> getCartItems();
    //Clear List
    void resetList();
    //Remove an Item
    void removeAnItem(Item item);
}
