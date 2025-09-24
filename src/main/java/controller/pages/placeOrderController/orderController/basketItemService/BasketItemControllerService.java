package controller.pages.placeOrderController.orderController.basketItemService;

import model.Item;
import model.dto.cart.CartItems;
import model.dto.cart.CartListVBox;

import java.util.List;

public interface BasketItemControllerService {
    List<CartListVBox> getCartListElements();

    //Return Cart Items Details
    List<CartItems> getCartItems();

    void addCartListElement(Item item, Integer count);

    //Return total values of the cart items - DOUBLE
    double getTotalInTheCart();

    //Return total Values of the caer items - STRING
    String getTotalString();

    //RESET the cart items
    void resetList();

    //Completely remove an item from the cart
    void removeAnItem(Item item);
}
