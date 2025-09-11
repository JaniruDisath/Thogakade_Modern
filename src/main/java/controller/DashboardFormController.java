package controller;

import db.DbChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    private Stage customerDetails = new Stage();
    private Stage itemDetails = new Stage();

    @FXML
    private Button customerDetailsButton;

    @FXML
    private Button itemDetailsButton;

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
    }


}
