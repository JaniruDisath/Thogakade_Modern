package controller.pages.orderController.serviceController;

import javafx.scene.layout.HBox;
import model.dto.cart.CartListVBox;

import java.util.List;

public interface OrderControllerService {
    List<HBox> getOrderCardList();

    void setOrderDetailsListUI(String orderID);

    List<CartListVBox> getCartListElements();
}
