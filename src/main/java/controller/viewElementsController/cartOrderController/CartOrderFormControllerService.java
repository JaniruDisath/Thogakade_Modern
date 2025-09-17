package controller.viewElementsController.cartOrderController;

import controller.placeOrderController.orderController.PlaceOrderControllerService;
import model.dto.cart.CartItems;

public interface CartOrderFormControllerService {
    void setData(CartItems cartItems, PlaceOrderControllerService service);
    void setCount(Integer count);
}
