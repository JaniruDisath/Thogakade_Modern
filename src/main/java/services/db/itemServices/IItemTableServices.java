package services.db.itemServices;

import model.Item;

public interface IItemTableServices {
    Item findItem(String itemCode);

    //Find and get price of an Item from Item Code
    Double getItemPrice(String itemCode);
}
