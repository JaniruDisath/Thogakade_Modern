package controller.viewElementsController.orderCardController;

import handlers.IHandler;
import model.Orders;

public interface OrderCardFormControllerService {

    void setData(Orders orders, Double total, IHandler handler);
}
