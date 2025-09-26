package services.db.orderServices;

import model.Orders;
import repository.OrdersTable.OrdersTable;
import repository.Thogakade_Modern;

import java.util.List;

public class OrdersTableServices implements IOrdersTableServices{

    //Repository Connection
    private Thogakade_Modern<Orders> thogakade_modern = new OrdersTable();
    //All the data on the table as a list
    private List<Orders> ordersList = thogakade_modern.getAllData();

    //Get All Data
    @Override
    public List<Orders> getAllOrderData() {
        return ordersList;
    }
}
