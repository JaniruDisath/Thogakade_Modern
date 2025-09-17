package controller.checkOutOrderController.formController;

import controller.checkOutOrderController.checkOutOrderController.CheckOutOrderControllerServices;
import controller.checkOutOrderController.checkOutOrderController.CheckoutOrderController;
import controller.customerController.services.CustomerController;
import controller.customerController.services.CustomerServices;
import controller.placeOrderController.formController.PlaceOrderFormControllerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.dto.cart.CartItems;
import model.dto.cart.CartList;
import org.controlsfx.control.SearchableComboBox;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class CheckOutOrderFormFormController implements CheckOutOrderFormControllerService {

    @FXML
    private SearchableComboBox<String> customerIDComboBox;

    @FXML
    private VBox orderVBox;

    @FXML
    private Label totalLabel;



    private final CheckOutOrderControllerServices services = new CheckoutOrderController();

    private List<CartList> list ;
    private String total;
    private List<CartItems> items;

    private String custID;

    private PlaceOrderFormControllerService placeOrderFormControllerService;



    public void setData(List<CartList> list, List<CartItems>items, String total, PlaceOrderFormControllerService placeOrderFormControllerService){
        this.list = list;
        this.total = total;
        this.items = items;
        this.placeOrderFormControllerService=placeOrderFormControllerService;
        setUIValues();
    }

    @FXML
    void onPlaceOrder(ActionEvent event) {
        custID = customerIDComboBox.getValue();
        services.updateCheckOutOrder(items,custID);
        sendConfirmation();
    }

    private void sendConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Are you confirming this order?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            placeOrderFormControllerService.cleanUI();
            closeWindow();
        }
    }

    public void setUIValues(){
        setCartListElement();
        updateTotal();
        loadComboBox();
    }

    private void loadComboBox(){
        CustomerServices customerServices = new CustomerController();
        customerIDComboBox.getItems().addAll(customerServices.getCustomerIds());
    }

    public void setCartListElement(){
        orderVBox.getChildren().clear();
        orderVBox.setSpacing(10.0);
        for (CartList elements : list) {
            orderVBox.getChildren().add(elements.getVBox());
        }
        updateTotal();
    }

    public void updateTotal(){
        totalLabel.setText(total);
    }

    private void closeWindow(){
        Stage stage = (Stage) orderVBox.getScene().getWindow();
        stage.close();
    }
}
