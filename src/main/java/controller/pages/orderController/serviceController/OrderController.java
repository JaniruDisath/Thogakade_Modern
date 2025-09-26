package controller.pages.orderController.serviceController;

import controller.pages.orderController.formController.OrderFormControllerService;
import services.interfaceElements.ordersCard.OrderListController;
import services.interfaceElements.ordersCard.OrderListControllerService;
import controller.pages.placeOrderController.orderController.basketItemService.BasketItemController;
import controller.pages.placeOrderController.orderController.basketItemService.BasketItemControllerService;
import handlers.IHandler;
import handlers.pages.OrderControllerHandler;
import javafx.scene.layout.HBox;
import model.OrderDetails;
import model.dto.cart.CartListVBox;
import services.calculations.Calculations;
import services.calculations.ICalculations;
import services.db.itemServices.IItemTableServices;
import services.db.itemServices.ItemTableServices;
import services.db.orderDetailServices.IOrderDetailsTableServices;
import services.db.orderDetailServices.OrderDetailsTableServices;

import java.util.List;

public class OrderController implements OrderControllerService {

    private OrderFormControllerService orderFormService;

    public OrderController(OrderFormControllerService orderFormService) {
        this.orderFormService = orderFormService;
    }

    //Handler - Order Controller Handler
    private IHandler handler = new OrderControllerHandler(this);

    private OrderListControllerService orderListService = new OrderListController(handler);
    private BasketItemControllerService basketItemerService = new BasketItemController(handler);

    //Service Connection - Order Details Table
    private IOrderDetailsTableServices orderDetailsTableServices = new OrderDetailsTableServices();
    //Service Connection - Item Table
    private IItemTableServices itemTableServices = new ItemTableServices();
    //Service Connection - Calculations
    private ICalculations calculations = new Calculations();



    @Override
    public List<HBox> getOrderCardList() {
        return orderListService.getOrderCardList();
    }

    @Override
    public void setOrderDetailsListUI(String orderID) {
        basketItemerService.resetList();
        loadTheBasketItems(orderDetailsTableServices.getOrderDetailsOfOrder(orderID));
        orderFormService.setTotalValue(calculations.getTotalOfAnOrder(orderID));
        orderFormService.refreshElements();
    }

    private void loadTheBasketItems(List<OrderDetails> selectedOrders) {
        for (int i = 0; i < selectedOrders.size(); i++) {
            basketItemerService.addCartListElement(itemTableServices.findItem(selectedOrders.get(i).getItemCode()), selectedOrders.get(i).getOrderQty());
        }
        setBasketItemController();
    }


    public void setBasketItemController() {
        for (int i = 0; i < getCartListElements().size(); i++) {
            getCartListElements().get(i).getControllerService().setData(basketItemerService.getCartItems().get(i));
            getCartListElements().get(i).getControllerService().setItemRemovable(false);
        }
    }


    @Override
    public List<CartListVBox> getCartListElements() {
        return basketItemerService.getCartListElements();
    }

}
