package controller.checkOutOrderController.checkOutOrderController;

import db.DBConnection;
import model.Order;
import model.OrderDetails;
import model.dto.cart.CartItems;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CheckoutOrderController implements CheckOutOrderControllerServices {

    @Override
    public void updateCheckOutOrder(List<CartItems> items, String customerID) {
        if (items==null||items.isEmpty()){
            return;
        }
        String nextOrderID = getNextOrderID();
        reduceItemQuantity(items);
        updateOrder(customerID,nextOrderID);
        addOrderDetails(items,nextOrderID);
    }

    //reduce item quantity from the inventory
    private void reduceItemQuantity(List<CartItems> items) {
        for (CartItems cartItems : items) {
            updateItemDetailsDB(cartItems);
        }
    }

    private void updateItemDetailsDB(CartItems cartItems) {
        String SQL = """
                UPDATE item
                SET qtyOnHand = ?
                WHERE itemCode = ?;
                """;

        try (PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(SQL)) {

            preparedStatement.setObject(1, (cartItems.getItem().getQtyOnHand()) - cartItems.getCount());
            preparedStatement.setObject(2, cartItems.getItem().getItemCode());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Item Updated Successfully ");
            } else {
                System.out.println("No Item Updated found with id: " + cartItems.getItem().getItemCode());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Update Orders

    private void updateOrder(String customerID, String orderID) {
        LocalDate today = LocalDate.now();
        updateOrderDB(new Order(orderID,today,customerID));
    }

    private void updateOrderDB(Order order) {
        String SQL = "INSERT INTO orders(orderID, orderDate, custID) VALUES(?,?,?);";
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(SQL);

            preparedStatement.setObject(1, order.getOrderID());
            preparedStatement.setObject(2, order.getOrderDate());
            preparedStatement.setObject(3, order.getCustID());
            System.out.println(order.getCustID());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private String getNextOrderID() {
        String currentOrderID = getTheLastOderID();
        int number = Integer.parseInt(currentOrderID.replaceAll("\\D", ""));
        System.out.println(number);
        number++;
        System.out.println(number);
        return "D" + (number < 100000 ? "0" : "") + number;
    }

    private String getTheLastOderID() {
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM orders ORDER BY orderID DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("orderID");
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Update order details

    private void addOrderDetails(List<CartItems> cartItems, String orderID){
        for(CartItems items : cartItems){
            addOrderDetailsToDB(new OrderDetails(orderID,items.getItem().getItemCode(),items.getCount(),0));
        }
    }

    private void addOrderDetailsToDB(OrderDetails orderDetails){
        String SQL = "INSERT INTO orderdetail(orderID, itemCode, orderQty,discount) VALUES(?,?,?,?);";
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(SQL);

            preparedStatement.setObject(1, orderDetails.getOrderID());
            preparedStatement.setObject(2, orderDetails.getItemCode());
            preparedStatement.setObject(3, orderDetails.getOrderQty());
            preparedStatement.setObject(4, orderDetails.getDiscount());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
