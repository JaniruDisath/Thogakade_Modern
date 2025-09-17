package controller.viewElementsController.itemCardController;

import controller.placeOrderController.orderController.PlaceOrderControllerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Item;

public class ItemCardFormController implements ItemCardFormControllerService {

    @FXML
    private ImageView itemImage;

    @FXML
    private Label titleLabel;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button increaseButton;
    //Todo get the available stock number and then add a limit for the increase button. ADD a separate label to show the available stock.

    @FXML
    private Button reduceButton;

    @FXML
    private TextField numberOfUnitsTextField;

    @FXML
    private Label priceLabel;


    private  Integer count = 1;
    private  PlaceOrderControllerService service;
    private Item item;


    public void setData(Item item, PlaceOrderControllerService service){
        this.service =service;
        this.item=item;
        updateUIValues();
    }

    public void updateUIValues(){
        itemImage.setImage(new Image("/images/productImages/"+item.getItemImage()));
        titleLabel.setText(item.getDescription());
        updateThePrice();
        updateTheCount();
    }

    private void updateTheCount(){
        numberOfUnitsTextField.setText(String.valueOf(count));
        updateThePrice();
        if (count<=1){
            reduceButton.setDisable(true);
        }else {
            reduceButton.setDisable(false);
        }
    }

    private void updateThePrice(){
        priceLabel.setText("Price : LKr. "+String.valueOf(item.getUnitPrice()*count));
    }


    @FXML
    void onDecrease(ActionEvent event) {
        count --;
        updateTheCount();
    }

    @FXML
    void onIncrease(ActionEvent event) {
        count ++;
        updateTheCount();

    }

    @FXML
    void onAddToCart(ActionEvent event) {
        service.addCartListElement(item, count);
        count=1;
        updateTheCount();
    }

    @Override
    public void changeCount(Integer count){
        this.count=count;
        updateUIValues();
    }

}
