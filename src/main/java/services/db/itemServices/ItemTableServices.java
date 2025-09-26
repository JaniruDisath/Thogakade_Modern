package services.db.itemServices;

import model.Item;
import repository.ItemTable.ItemTable;
import repository.Thogakade_Modern;

import java.util.List;
import java.util.Objects;

public class ItemTableServices implements IItemTableServices{

    //Repository Connection
    private Thogakade_Modern<Item> thogakade_modern = new ItemTable();

    //All the data on the table as a list
    private List<Item> itemList = thogakade_modern.getAllData();

    //Find and get An Item From Item Code
    @Override
    public Item findItem(String itemCode) {
        for (int i = 0; i < itemList.size(); i++) {
            if (Objects.equals(itemCode, itemList.get(i).getItemCode())) {
                return itemList.get(i);
            }
        }
        return null;
    }

    //Find and get price of an Item from Item Code
    @Override
    public Double getItemPrice(String itemCode){
        return findItem(itemCode).getUnitPrice();
    }
}
