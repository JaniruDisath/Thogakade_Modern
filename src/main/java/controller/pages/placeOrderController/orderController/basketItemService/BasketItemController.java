package controller.pages.placeOrderController.orderController.basketItemService;

import controller.viewElementsController.cartOrderController.CartOrderFormControllerService;
import handlers.IHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import model.Item;
import model.dto.cart.CartItems;
import model.dto.cart.CartListVBox;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BasketItemController implements BasketItemControllerService {

    private final IHandler handler;

    public BasketItemController(IHandler handler){
        this.handler=handler;
    }

    private final List<CartItems> cartItems = new ArrayList<>();
    private final List<CartListVBox> cartListVBoxElements = new ArrayList<>();


    //Return Cart Items UI Cards
    @Override
    public List<CartListVBox> getCartListElements() {
        return cartListVBoxElements;
    }

    //Return Cart Items Details
    @Override
    public List<CartItems> getCartItems() {
        return cartItems;
    }

    private void addCartListCard(Item item, Integer count) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/elements/cartOrder.fxml"));
            VBox itemCard = loader.load();

            CartOrderFormControllerService controller = loader.getController();
            controller.setData(new CartItems(item, count),handler);

            addItemsToTheList(item, count, itemCard, controller);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addItemsToTheList(Item item, Integer count, VBox itemCard, CartOrderFormControllerService controller) {
        cartItems.add(new CartItems(item, count));
        cartListVBoxElements.add(new CartListVBox(itemCard, controller));
    }

    @Override
    public void addCartListElement(Item item, Integer count) {
        int listPosition = 0;
        for (CartItems cartItem : cartItems) {
            if (cartItem.getItem().getItemCode().equals(item.getItemCode())) {
                // Product already exists → increase count
                int newCount = cartItem.getCount() + count;
                // Assuming you have a setter for count
                cartItem.setCount(newCount);
                cartListVBoxElements.get(listPosition).getControllerService().setCount(newCount);
                return; // Exit after updating
            }
            listPosition++;
        }
        addCartListCard(item, count);
    }

    //Return total values of the cart items - DOUBLE
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

    //Return total Values of the caer items - STRING
    @Override
    public String getTotalString() {
        return NumberFormat.getNumberInstance(Locale.US).format(getTotalInTheCart()) + ".00";
    }

    //RESET the cart items
    @Override
    public void resetList() {
        cartItems.clear();
        cartListVBoxElements.clear();
    }

    //Completely remove an item from the cart
    @Override
    public void removeAnItem(Item item) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getItem().getItemCode().equals(item.getItemCode())) {
                cartItems.remove(i);
                cartListVBoxElements.remove(i);
            }
        }
    }
}
