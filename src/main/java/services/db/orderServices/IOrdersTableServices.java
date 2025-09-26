package services.db.orderServices;

import model.Orders;

import java.util.List;

public interface IOrdersTableServices {
    //Get All Data
    List<Orders> getAllOrderData();
}
