package controller.viewElementsController.orderCardController;

import handlers.IHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Orders;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class OrderCardFormController implements OrderCardFormControllerService {

    @FXML
    private Label orderIDLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label customerIDLabel;

    @FXML
    private Label totalValueLabel;

    private IHandler handler;

    private Orders orders;

    @Override
    public void setData(Orders orders, Double total, IHandler handler){
        setOrderID(orders.getOrderID());
        setOrderDate(orders.getOrderDate());
        setCustomerID(orders.getCustomerID());
        setTotalValue(total);
        this.orders=orders;
        this.handler = handler;
    }

    @FXML
    void onOrderCardClicked(MouseEvent event) {
        handler.onOrderCardClick(orders.getOrderID());
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
        totalValueLabel.setText( NumberFormat.getNumberInstance(Locale.US).format(totalValue) + ".00");
    }
}
