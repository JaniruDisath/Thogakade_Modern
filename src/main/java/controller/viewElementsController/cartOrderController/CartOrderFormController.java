package controller.viewElementsController.cartOrderController;

import controller.pages.placeOrderController.orderController.PlaceOrderControllerService;
import handlers.IHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dto.cart.CartItems;

public class CartOrderFormController implements CartOrderFormControllerService {

    @FXML
    private Button decreaseOrder;

    @FXML
    private Button removeButton;

    @FXML
    private Button increaseOrder;

    @FXML
    private TextField numberOfUnitsTextField;

    @FXML
    private Label priceLabel;

    @FXML
    private Label titleLabel;

    private IHandler handler;
    private CartItems cartItems;

    @FXML
    void onDecrease(ActionEvent event) {
        handler.itemCardAddItem(cartItems.getItem(), -1);
    }

    @FXML
    void onIncrease(ActionEvent event) {
        handler.itemCardAddItem(cartItems.getItem(), 1);
    }

    @Override
    public void setData(CartItems cartItems, IHandler handler) {
        setTitle(cartItems.getItem().getDescription());
        setPrice(cartItems.getItem().getUnitPrice());
        setCount(cartItems.getCount());
        this.handler = handler;
        this.cartItems = cartItems;
    }

    @Override
    public void setData(CartItems cartItems) {
        setTitle(cartItems.getItem().getDescription());
        setPrice(cartItems.getItem().getUnitPrice());
        setCount(cartItems.getCount());
        this.cartItems = cartItems;
    }

    @Override
    public void setItemRemovable(Boolean removable){
        removeButton.setVisible(removable);
        increaseOrder.setVisible(removable);
        decreaseOrder.setVisible(removable);
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
        handler.basketItemRemove(cartItems.getItem());
    }


}
