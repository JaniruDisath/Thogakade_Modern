package services.db.orderDetailServices;

import model.OrderDetails;

import java.util.List;

public interface IOrderDetailsTableServices {
    //Searching ang collect items of the same oder ID
    List<OrderDetails> getOrderDetailsOfOrder(String searchOrderID);

}
