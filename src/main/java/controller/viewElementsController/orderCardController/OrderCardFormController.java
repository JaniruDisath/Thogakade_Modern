package controller.viewElementsController.orderCardController;

import controller.pages.orderController.serviceController.OrderControllerService;
import controller.pages.orderController.serviceController.orderListServices.OrderListControllerService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Orders;

import java.time.LocalDate;

public class OrderCardFormController implements OrderCardFormControllerService {

    @FXML
    private Label orderIDLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label customerIDLabel;

    @FXML
    private Label totalValueLabel;

    private OrderControllerService orderControllerService;

    private Orders orders;

    @Override
    public void setData(Orders orders, Double total, OrderControllerService orderControllerService){
        setOrderID(orders.getOrderID());
        setOrderDate(orders.getOrderDate());
        setCustomerID(orders.getCustomerID());
        setTotalValue(total);
        this.orders=orders;
        this.orderControllerService = orderControllerService;
    }

    @FXML
    void onOrderCardClicked(MouseEvent event) {
        orderControllerService.setOrderDetailsListUI(orders.getOrderID());
    }


    public void setOrderID(String orderID) {
        orderIDLabel.setText(orderID);
    }

    public void setOrderDate(LocalDate orderDate) {
        orderDateLabel.setText(String.valueOf(orderDate));
    }

    public void setCustomerID(String customerID) {
        customerIDLabel.setText(customerID);
    }

    public void setTotalValue(Double totalValue) {
        totalValueLabel.setText(String.valueOf(totalValue));
    }




}
