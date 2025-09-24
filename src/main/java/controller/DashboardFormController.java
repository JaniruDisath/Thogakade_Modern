package controller;

import db.DbChecker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    private Stage customerDetails = new Stage();


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
        loadUI("/view/pages/PlaceOrder.fxml");
    }

    @FXML
    void onOrderElementClicked(MouseEvent event) {
        loadUI("/view/pages/OrderDetails.fxml");
    }

    @FXML
    void onInventoryElementClicked(MouseEvent event) {
        loadUI("/view/pages/ItemDetails.fxml");
    }

    @FXML
    void onCustomersElementClicked(MouseEvent event) {
        loadUI("/view/pages/CustomerDetails.fxml");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new DbChecker().initializeDatabase();
        loadUI("/view/pages/PlaceOrder.fxml");

    }



}
