package controller.placeOrderController.orderController;

import controller.placeOrderController.formController.PlaceOrderFormController;
import controller.placeOrderController.formController.PlaceOrderFormControllerService;
import controller.viewElementsController.cartOrderController.CartOrderFormControllerService;
import controller.viewElementsController.itemCardController.ItemCardFormController;
import db.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Item;
import model.dto.cart.CartItems;
import model.dto.cart.CartList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PlaceOrderController implements PlaceOrderControllerService {

    private PlaceOrderFormControllerService ui;

    public PlaceOrderController(PlaceOrderFormControllerService ui) {
        this.ui = ui;
    }


    //Initializing the grocery items
    private HBox currentRow;
    private List<HBox> finalColumn = new ArrayList<>();
    private int cardCount = 0;
    private final int MAX_CARDS_PER_ROW = 5;


    //Setting Grocery Elements on the Dashboard
    @Override
    public List<HBox> getItemData(PlaceOrderFormController controller) {
        finalColumn.clear();
        List<Item> itemDetails = getAllCustomerDetails();
        for (int i = 0; i < itemDetails.size(); i++) {
            loadItemsPanel(itemDetails.get(i));
        }
        return finalColumn;
    }

    private void loadItemsPanel(Item item) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/elements/ItemCard.fxml"));
            AnchorPane itemCard = loader.load();

            ItemCardFormController controller = loader.getController();
            controller.setData(item, this);

            if (currentRow == null || cardCount >= MAX_CARDS_PER_ROW) {
                currentRow = new HBox(10);
                finalColumn.add(currentRow);
                cardCount = 0;
            }

            currentRow.getChildren().add(itemCard);
            cardCount++;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getAllCustomerDetails() {
        List<Item> itemDetails = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("Select * FROM item;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                itemDetails.add(new Item(
                                resultSet.getString("itemCode"),
                                resultSet.getString("description"),
                                resultSet.getString("packSize"),
                                resultSet.getDouble("unitPrice"),
                                resultSet.getInt("qtyOnHand"),
                                resultSet.getString("itemImage")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemDetails;
    }

    private final List<CartItems> cartItems = new ArrayList<>();
    private final List<CartList> cartListElements = new ArrayList<>();

    //Adding Elements to the cart
    @Override
    public List<CartList> getCartListElements() {
        return cartListElements;
    }

    public List<CartItems> getCartItems() {
        return cartItems;
    }

    @Override
    public void addCartListElement(Item item, Integer count) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/elements/cartOrder.fxml"));
            VBox itemCard = loader.load();

            CartOrderFormControllerService controller = loader.getController();
            controller.setData(new CartItems(item, count), this);

            int listPosition = 0;
            for (CartItems cartItem : cartItems) {
                if (cartItem.getItem().getItemCode().equals(item.getItemCode())) {
                    // Product already exists → increase count
                    int newCount = cartItem.getCount() + count;
                    // Assuming you have a setter for count
                    cartItem.setCount(newCount);
                    ui.updateUIValues();
                    cartListElements.get(listPosition).getControllerService().setCount(newCount);
                    return; // Exit after updating
                }
                listPosition++;
            }
            cartItems.add(new CartItems(item, count));
            cartListElements.add(new CartList(itemCard, controller));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui.setCartListElement();
    }

    @Override
    public double getTotalInTheCart() {
        double total = 0.0;
        for (CartItems cartItem : cartItems) {
            double unitPrice = cartItem.getItem().getUnitPrice();
            int count = cartItem.getCount();
            total += unitPrice * count;
        }
        return total;
    }

    public String getTotalString() {
        return NumberFormat.getNumberInstance(Locale.US).format(getTotalInTheCart()) + ".00";
    }

    public void resetList() {
        finalColumn.clear();
        cartItems.clear();
        cartListElements.clear();
    }

    public void removeAnItem(Item item) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getItem().getItemCode().equals(item.getItemCode())) {
                cartItems.remove(i);
                cartListElements.remove(i);
                ui.updateUIValues();
                // ui.setElements();
            }
        }
    }
}

