package model.dto.cart;

import controller.viewElementsController.cartOrderController.CartOrderFormControllerService;
import javafx.scene.layout.VBox;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartList {
    private VBox vBox;
    private CartOrderFormControllerService controllerService;
}
