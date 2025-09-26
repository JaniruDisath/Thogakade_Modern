package controller.pages.orderController.formController;

import controller.pages.orderController.serviceController.OrderController;
import controller.pages.orderController.serviceController.OrderControllerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.dto.cart.CartListVBox;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable,OrderFormControllerService {

    private final OrderControllerService service =new OrderController(this);

    @FXML
    private VBox itemsVBox;

    @FXML
    private VBox orderVBox;

    @FXML
    private Label totalLabel;

    @FXML
    void onChangeOrder(ActionEvent event) {

    }

    @Override
    public void setTotalValue(Double totalValue){
        totalLabel.setText( NumberFormat.getNumberInstance(Locale.US).format(totalValue) + ".00");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTheOrders();
        itemsVBox.setFocusTraversable(false);
    }

    private void loadTheOrders(){
        for(HBox items : service.getOrderCardList()){
            itemsVBox.getChildren().add(items);
        }
    }

    @Override
    public void refreshElements(){
        loadTheBasketItems();
    }

    public void loadTheBasketItems(){
        orderVBox.getChildren().clear();
        orderVBox.setSpacing(10.0);
        for (CartListVBox elements : service.getCartListElements()) {
            orderVBox.getChildren().add(elements.getVBox());
        }
    }
}
