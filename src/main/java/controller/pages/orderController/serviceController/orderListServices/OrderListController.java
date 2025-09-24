package controller.pages.orderController.serviceController.orderListServices;

import controller.pages.orderController.serviceController.OrderControllerService;
import controller.viewElementsController.orderCardController.OrderCardFormControllerService;
import db.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import model.Orders;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        List<Orders> itemDetails = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("Select * FROM orders;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                itemDetails.add(new Orders(
                                resultSet.getString("orderID"),
                                resultSet.getDate("orderDate").toLocalDate(),
                                resultSet.getString("custID")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemDetails;
    }

}
