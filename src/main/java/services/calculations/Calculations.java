package services.calculations;

import model.OrderDetails;
import services.db.itemServices.IItemTableServices;
import services.db.itemServices.ItemTableServices;
import services.db.orderDetailServices.IOrderDetailsTableServices;
import services.db.orderDetailServices.OrderDetailsTableServices;

public class Calculations implements ICalculations {

    //Service Connection - Order Details Table
    private IOrderDetailsTableServices orderDetailsTableServices = new OrderDetailsTableServices();
    //Service Connection - Item Table
    private IItemTableServices itemTableServices = new ItemTableServices();

    //Searching and get total of an Order
    @Override
    public Double getTotalOfAnOrder(String orderID){
        Double total = 0.0;
        for (OrderDetails orderDetails:orderDetailsTableServices.getOrderDetailsOfOrder(orderID)){
            total+=(orderDetails.getOrderQty()* itemTableServices.getItemPrice(orderDetails.getItemCode()));
        }
        return total;
    }

}
