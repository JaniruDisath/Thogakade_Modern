package controller;

import db.DbChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    private Stage customerDetails = new Stage();
    private Stage itemDetails = new Stage();

    @FXML
    private HBox dashboardElement;

    @FXML
    private HBox orderElement;

    @FXML
    private HBox inventoryElement;

    @FXML
    private HBox customerElement;

    //On Mouse Click Listeners

    @FXML
    void onDashboardElementClicked(MouseEvent event) {
        loadUI("/view/PlaceOrder.fxml");
    }

    @FXML
    void onOrderElementClicked(MouseEvent event) {

    }

    @FXML
    void onInventoryElementClicked(MouseEvent event) {
        loadUI("/view/ItemDetails.fxml");
    }

    @FXML
    void onCustomersElementClicked(MouseEvent event) {
        loadUI("/view/CustomerDetails.fxml");
    }






    @FXML
    private StackPane contentArea;

    private void loadUI(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            contentArea.getChildren().setAll(root); // replace everything in contentArea
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onCustomerButton(ActionEvent event) {
        try {
            customerDetails.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerDetails.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        customerDetails.setResizable(false);
        customerDetails.show();
    }

    @FXML
    void onItemDetails(ActionEvent event) {
        try {
            itemDetails.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemDetails.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemDetails.setResizable(false);
        itemDetails.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new DbChecker().initializeDatabase();
        loadUI("/view/PlaceOrder.fxml");
    }



}
