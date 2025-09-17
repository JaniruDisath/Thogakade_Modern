package controller.checkOutOrderController.formController;

import controller.placeOrderController.formController.PlaceOrderFormController;
import controller.placeOrderController.formController.PlaceOrderFormControllerService;
import model.dto.cart.CartItems;
import model.dto.cart.CartList;

import java.util.List;

public interface CheckOutOrderFormControllerService {
    void setData(List<CartList> list, List<CartItems>items, String total, PlaceOrderFormControllerService placeOrderFormControllerService);
}
