package services.db.orderDetailServices;

import model.OrderDetails;
import repository.OrderDetailTable.OrderDetailTable;
import repository.Thogakade_Modern;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDetailsTableServices implements IOrderDetailsTableServices {

    //Repository Connection
    private Thogakade_Modern<OrderDetails> thogakade_modern = new OrderDetailTable();

    //All the data on the table as a list
    private List<OrderDetails> allOrderDetailsList = thogakade_modern.getAllData();


    //Searching and get collected items of the same oder ID
    @Override
    public List<OrderDetails> getOrderDetailsOfOrder(String searchOrderID){
        List<OrderDetails> selectedOrdersItems = new ArrayList<>();
        for (int i = 0; i < allOrderDetailsList.size(); i++) {
            if (Objects.equals(searchOrderID, allOrderDetailsList.get(i).getOrderID())) {
                selectedOrdersItems.add(allOrderDetailsList.get(i));
            }
        }
        return selectedOrdersItems;
    }

}
