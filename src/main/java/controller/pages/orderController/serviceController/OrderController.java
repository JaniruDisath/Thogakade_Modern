package controller.pages.orderController.serviceController;

import controller.pages.orderController.formController.OrderFormControllerService;
import controller.pages.orderController.serviceController.orderListServices.OrderListController;
import controller.pages.orderController.serviceController.orderListServices.OrderListControllerService;
import controller.pages.placeOrderController.orderController.basketItemService.BasketItemController;
import controller.pages.placeOrderController.orderController.basketItemService.BasketItemControllerService;
import javafx.scene.layout.HBox;
import model.Item;
import model.OrderDetails;
import model.dto.cart.CartListVBox;
import repository.ItemTable.ItemTable;
import repository.OrderDetailTable.OrderDetailTable;
import repository.Thogakade_Modern;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderController implements OrderControllerService {

    private OrderFormControllerService orderFormControllerService;

    public OrderController(OrderFormControllerService orderFormControllerService) {
        this.orderFormControllerService = orderFormControllerService;
    }

    private OrderListControllerService orderListControllerService = new OrderListController(this);
    private BasketItemControllerService basketItemControllerService = new BasketItemController();

    @Override
    public List<HBox> getOrderCardList() {
        return orderListControllerService.getOrderCardList();
    }

    @Override
    public void setOrderDetailsListUI(String orderID) {
        basketItemControllerService.resetList();
        Thogakade_Modern<OrderDetails> thogakade_modern = new OrderDetailTable();
        List<OrderDetails> orderDetailsList = thogakade_modern.getAllData();
        List<OrderDetails> selectedOrders = new ArrayList<>();
        for (int i = 0; i < orderDetailsList.size(); i++) {
            if (Objects.equals(orderID, orderDetailsList.get(i).getOrderID())) {
                selectedOrders.add(orderDetailsList.get(i));
            }
        }
        loadTheBasketItems(selectedOrders);
        orderFormControllerService.refreshElements();
    }

    private void loadTheBasketItems(List<OrderDetails> selectedOrders) {
        for (int i = 0; i < selectedOrders.size(); i++) {
            basketItemControllerService.addCartListElement(findItem(selectedOrders.get(i).getItemCode()), selectedOrders.get(i).getOrderQty());
        }
        setBasketItemController();
    }


    public void setBasketItemController() {
        for (int i = 0; i < getCartListElements().size(); i++) {
            getCartListElements().get(i).getControllerService().setData(basketItemControllerService.getCartItems().get(i));
            getCartListElements().get(i).getControllerService().setItemRemovable(false);
        }
    }

    private Item findItem(String itemCode) {
        Thogakade_Modern<Item> thogakade_modern = new ItemTable();
        List<Item> itemList = thogakade_modern.getAllData();
        for (int i = 0; i < itemList.size(); i++) {
            if (Objects.equals(itemCode, itemList.get(i).getItemCode())) {
                return itemList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<CartListVBox> getCartListElements() {
        return basketItemControllerService.getCartListElements();
    }

}
