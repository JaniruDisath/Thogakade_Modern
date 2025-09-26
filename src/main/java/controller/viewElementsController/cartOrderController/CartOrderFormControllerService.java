package controller.viewElementsController.cartOrderController;

import controller.pages.placeOrderController.orderController.PlaceOrderControllerService;
import handlers.IHandler;
import model.dto.cart.CartItems;

public interface CartOrderFormControllerService {

    void setData(CartItems cartItems, IHandler handler);

    void setData(CartItems cartItems);

    void setItemRemovable(Boolean removable);

    void setCount(Integer count);
}
