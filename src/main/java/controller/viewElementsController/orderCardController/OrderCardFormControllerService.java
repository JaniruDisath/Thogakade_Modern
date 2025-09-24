package controller.viewElementsController.orderCardController;

import controller.pages.orderController.serviceController.OrderControllerService;
import controller.pages.orderController.serviceController.orderListServices.OrderListControllerService;
import model.Orders;

import java.time.LocalDate;

public interface OrderCardFormControllerService {

    void setData(Orders orders, Double total, OrderControllerService orderControllerService);
}
