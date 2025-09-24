package controller.windows.checkOutOrderController.formController;

import controller.pages.placeOrderController.formController.PlaceOrderFormControllerService;
import model.dto.cart.CartItems;
import model.dto.cart.CartListVBox;

import java.util.List;

public interface CheckOutOrderFormControllerService {
    void setData(List<CartListVBox> list, List<CartItems>items, String total, PlaceOrderFormControllerService placeOrderFormControllerService);
}
