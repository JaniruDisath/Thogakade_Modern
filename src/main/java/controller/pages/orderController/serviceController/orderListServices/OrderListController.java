package controller.pages.orderController.serviceController.orderListServices;

import controller.pages.orderController.serviceController.OrderControllerService;
import controller.viewElementsController.orderCardController.OrderCardFormControllerService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import model.Orders;
import repository.OrdersTable.OrdersTable;
import repository.Thogakade_Modern;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderListController implements OrderListControllerService {

    private OrderControllerService orderControllerService;

    public OrderListController(OrderControllerService orderControllerService){
        this.orderControllerService=orderControllerService;
    }

    private final List<HBox> orderCardList = new ArrayList<>();

    @Override
    public List<HBox> getOrderCardList() {
        orderCardList.clear();
        loadOrderDetails();
        return orderCardList;
    }

    public void loadOrderDetails() {
        for (Orders orders : getAllOrderData()) {
            setTheUI(orders,0.0);
        }
    }

    public void setTheUI(Orders orders, Double total) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/elements/OrderDetailCard.fxml"));
        HBox orderCard;
        try {
            orderCard = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        OrderCardFormControllerService controller = loader.getController();
        controller.setData(orders, total,orderControllerService);
        orderCardList.add(orderCard);
    }

    private List<Orders> getAllOrderData() {
        Thogakade_Modern<Orders> thogakade_modern = new OrdersTable();
        return thogakade_modern.getAllData();
    }

}
