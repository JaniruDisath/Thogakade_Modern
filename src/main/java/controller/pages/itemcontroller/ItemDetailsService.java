package controller.pages.itemcontroller;

import javafx.collections.ObservableList;
import model.Item;

public interface ItemDetailsService {
    void addItemDetails(Item item);
    void deleteItemDetails(String itemId);
    void updateItemDetails(Item item);
    ObservableList<Item> getAllCustomerDetails();
}
