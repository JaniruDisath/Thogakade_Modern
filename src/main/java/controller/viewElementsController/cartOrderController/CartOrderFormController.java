package controller.viewElementsController.cartOrderController;

import controller.placeOrderController.orderController.PlaceOrderControllerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.dto.cart.CartItems;

public class CartOrderFormController implements CartOrderFormControllerService {

    @FXML
    private Button decreaseOrder;

    @FXML
    private Button increaseOrder;

    @FXML
    private TextField numberOfUnitsTextField;

    @FXML
    private Label priceLabel;

    @FXML
    private Label titleLabel;

    private PlaceOrderControllerService service;
    private CartItems cartItems;

    @FXML
    void onDecrease(ActionEvent event) {
        service.addCartListElement(cartItems.getItem(), -1);
    }

    @FXML
    void onIncrease(ActionEvent event) {
        service.addCartListElement(cartItems.getItem(), 1);
    }

    @Override
    public void setData(CartItems cartItems, PlaceOrderControllerService service) {
        setTitle(cartItems.getItem().getDescription());
        setPrice(cartItems.getItem().getUnitPrice());
        setCount(cartItems.getCount());
        this.service = service;
        this.cartItems = cartItems;
    }

    private void setTitle(String title) {
        titleLabel.setText(title);
    }

    private void setPrice(Double price) {
        priceLabel.setText("Price : LKr. " + String.valueOf(price));

    }

    @Override
    public void setCount(Integer count) {
        numberOfUnitsTextField.setText(String.valueOf(count));
        if (count <= 1) {
            decreaseOrder.setDisable(true);
        } else {
            decreaseOrder.setDisable(false);
        }
    }

    @FXML
    void onDeleteItem(ActionEvent event) {
        service.removeAnItem(cartItems.getItem());
    }


}
