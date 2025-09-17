package model.dto.cart;

import lombok.*;
import model.Item;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItems {

    private Item item;
    private Integer count;

}
