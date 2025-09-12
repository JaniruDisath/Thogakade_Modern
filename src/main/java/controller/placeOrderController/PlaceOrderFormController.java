package controller.placeOrderController;

import controller.viewElementsController.ItemCardFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private VBox itemsVBox;

    @FXML
    private VBox orderVBox;

    @FXML
    private Label totalLabel;

    @FXML
    void onAddOrder(ActionEvent event) {

    }

    private HBox currentRow;
    private int cardCount = 0;
    private final int MAX_CARDS_PER_ROW = 3;

    private void loadItemsPanel() {

        try {
            // Load the ItemCard.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/elements/ItemCard.fxml"));
            AnchorPane itemCard = loader.load();

            // --- if you need to pass data ---
//            ItemCardController controller = loader.getController();
//            controller.setData(value);

            // If no current row or row is full → make a new one
            if (currentRow == null || cardCount >= MAX_CARDS_PER_ROW) {
                currentRow = new HBox(10); // 10 = spacing
                itemsVBox.getChildren().add(currentRow);
                cardCount = 0;
            }

            // Add card to current row
            currentRow.getChildren().add(itemCard);
            cardCount++;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHBox() {
        HBox hbox = new HBox(100);


        Button btn1 = new Button("Button 1");
        Button btn2 = new Button("Button 2");

        hbox.getChildren().addAll(btn1, btn2);

        itemsVBox.getChildren().add(hbox);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemsPanel();
        loadItemsPanel();
        loadItemsPanel();
    }
}
