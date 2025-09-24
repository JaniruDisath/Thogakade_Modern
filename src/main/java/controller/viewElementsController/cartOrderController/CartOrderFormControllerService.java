package controller.viewElementsController.cartOrderController;

import controller.pages.placeOrderController.orderController.PlaceOrderControllerService;
import model.dto.cart.CartItems;

public interface CartOrderFormControllerService {

    void setData(CartItems cartItems, PlaceOrderControllerService service);

    void setData(CartItems cartItems);

    void setItemRemovable(Boolean removable);

    void setCount(Integer count);
}
