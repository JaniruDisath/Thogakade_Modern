package controller.pages.placeOrderController.formController;

import controller.windows.checkOutOrderController.formController.CheckOutOrderFormControllerService;
import controller.pages.placeOrderController.orderController.PlaceOrderController;
import controller.pages.placeOrderController.orderController.PlaceOrderControllerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.dto.cart.CartListVBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable, PlaceOrderFormControllerService {

    private Stage chekOutStage = new Stage();
    @FXML
    private VBox itemsVBox;

    @FXML
    private VBox orderVBox;

    @FXML
    private Label totalLabel;

    private PlaceOrderControllerService service = new PlaceOrderController(this);


    //Open Final Check Out Window
    @FXML
    void onAddOrder(ActionEvent event) {

        if (service.getCartListElements()==null || service.getCartListElements().isEmpty()){
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/elements/CheckOutOrder.fxml"));
        Parent root;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CheckOutOrderFormControllerService controller = loader.getController();
        controller.setData(service.getCartListElements(),service.getCartItems(),service.getTotalString(),this);

        chekOutStage.setScene(new Scene(root));
        chekOutStage.setResizable(false);
        chekOutStage.show();

    }


    //Initialize Elements
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setElements();
    }

    //Set Menu Elements
    @Override
    public void setElements(){
        for(HBox items : service.getItemData(this)){
            itemsVBox.getChildren().add(items);
        }
        updateTotal();
    }

    //Set Shopping cart elements
    public void setCartListElement(){
        orderVBox.getChildren().clear();
        orderVBox.setSpacing(10.0);
        for (CartListVBox elements : service.getCartListElements()) {
            orderVBox.getChildren().add(elements.getVBox());
        }
    }


    //Set Total
    public void updateTotal(){
        totalLabel.setText(service.getTotalString());
    }


    //Update Cart Items and the total
    @Override
    public void updateCartUIValues(){
        setCartListElement();
        updateTotal();
    }

    @Override
    public void cleanUI(){
        service.resetList();
        updateTotal();
        setElements();
    }

}
