package controller.pages.orderController.serviceController;

import controller.pages.orderController.formController.OrderFormControllerService;
import controller.pages.orderController.serviceController.orderDetailsListServices.OrderDetailsListController;
import controller.pages.orderController.serviceController.orderDetailsListServices.OrderDetailsListControllerService;
import controller.pages.orderController.serviceController.orderListServices.OrderListController;
import controller.pages.orderController.serviceController.orderListServices.OrderListControllerService;
import controller.pages.orderController.serviceController.stockItemsListService.StockItemsListController;
import controller.pages.orderController.serviceController.stockItemsListService.StockItemsListInterface;
import controller.pages.placeOrderController.orderController.basketItemService.BasketItemController;
import controller.pages.placeOrderController.orderController.basketItemService.BasketItemControllerService;
import javafx.scene.layout.HBox;
import model.Item;
import model.OrderDetails;
import model.dto.cart.CartListVBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderController implements OrderControllerService {

    private OrderFormControllerService orderFormControllerService;

    public OrderController(OrderFormControllerService orderFormControllerService) {
        this.orderFormControllerService = orderFormControllerService;
    }

    private OrderListControllerService orderListControllerService = new OrderListController(this);
    private OrderDetailsListControllerService orderDetailsListControllerService = new OrderDetailsListController();
    private BasketItemControllerService basketItemControllerService = new BasketItemController();
    private StockItemsListInterface stockItemsListInterface = new StockItemsListController();

    @Override
    public List<HBox> getOrderCardList() {
        return orderListControllerService.getOrderCardList();
    }

    @Override
    public void setOrderDetailsListUI(String orderID) {
        basketItemControllerService.resetList();
        List<OrderDetails> orderDetailsList = orderDetailsListControllerService.getOrderDetailsList();
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
        List<Item> itemList = stockItemsListInterface.getItemsList();
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
