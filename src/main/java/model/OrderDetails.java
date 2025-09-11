package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetails {
    private String orderID ;
    private String itemCode ;
    private Integer orderQty ;
    private Integer discount ;
}
