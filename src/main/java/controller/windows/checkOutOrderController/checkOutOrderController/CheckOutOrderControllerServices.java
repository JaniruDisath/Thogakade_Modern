package controller.windows.checkOutOrderController.checkOutOrderController;

import model.dto.cart.CartItems;

import java.util.List;

public interface CheckOutOrderControllerServices {
    void updateCheckOutOrder(List<CartItems> items, String customerID);
}
