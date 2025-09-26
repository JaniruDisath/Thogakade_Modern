package handlers;

import model.Item;

public interface IHandler {
    void onOrderCardClick(String orderID);
    void itemCardAddItem(Item item, Integer count);
    void basketItemRemove(Item item);

}
