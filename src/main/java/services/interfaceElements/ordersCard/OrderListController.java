package services.interfaceElements.ordersCard;

import controller.viewElementsController.orderCardController.OrderCardFormControllerService;
import handlers.IHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import model.Orders;
import services.calculations.Calculations;
import services.calculations.ICalculations;
import services.db.orderServices.IOrdersTableServices;
import services.db.orderServices.OrdersTableServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderListController implements OrderListControllerService {

    //Handler
    private IHandler handler;

    public OrderListController(IHandler handler){
        this.handler=handler;
    }

    //Service Connection - Calculations
    private ICalculations calculations = new Calculations();
    //Service Connection - Order Table Service
    private IOrdersTableServices ordersTableServices = new OrdersTableServices();
    //Order Card  List of All Orders
    private final List<HBox> orderCardList = new ArrayList<>();

    //Returning Order UI elements
    @Override
    public List<HBox> getOrderCardList() {
        loadOrderDetails();
        return orderCardList;
    }
    //Load The Details to the UI
    private void loadOrderDetails() {
        orderCardList.clear();
        for (Orders orders : ordersTableServices.getAllOrderData()) {
            setTheUI(orders, calculations.getTotalOfAnOrder(orders.getOrderID()));
        }
    }
    //Loading UI and adding data
    private void setTheUI(Orders orders, Double total) {
        FXMLLoader loader = getUICard();
        HBox orderCard;
        try {
            orderCard = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        OrderCardFormControllerService controller = loader.getController();
        controller.setData(orders, total,handler);
        orderCardList.add(orderCard);
    }
    //Load the UI
    private FXMLLoader getUICard(){
        return new FXMLLoader(getClass().getResource("/view/elements/OrderDetailCard.fxml"));
    }

}
