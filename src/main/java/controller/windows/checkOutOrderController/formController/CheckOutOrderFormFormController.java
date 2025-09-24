package controller.windows.checkOutOrderController.formController;

import controller.windows.checkOutOrderController.checkOutOrderController.CheckOutOrderControllerServices;
import controller.windows.checkOutOrderController.checkOutOrderController.CheckoutOrderController;
import controller.pages.placeOrderController.formController.PlaceOrderFormControllerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;
import model.dto.cart.CartItems;
import model.dto.cart.CartListVBox;
import org.controlsfx.control.SearchableComboBox;
import repository.CustomerTable.CustomerTable;
import repository.Thogakade_Modern;

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

    private List<CartListVBox> list ;
    private String total;
    private List<CartItems> items;

    private String custID;

    private PlaceOrderFormControllerService placeOrderFormControllerService;



    @Override
    public void setData(List<CartListVBox> list, List<CartItems>items, String total, PlaceOrderFormControllerService placeOrderFormControllerService){
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
        Thogakade_Modern<Customer> thogakade_modern = new CustomerTable();
        customerIDComboBox.getItems().addAll(thogakade_modern.getListOfPrimaryKeys());
    }

    public void setCartListElement(){
        orderVBox.getChildren().clear();
        orderVBox.setSpacing(10.0);
        for (CartListVBox elements : list) {
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
