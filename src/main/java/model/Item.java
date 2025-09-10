package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private  String itemCode;
    private  String description;
    private String packSize;
    private Double unitPrice;
    private  Integer qtyOnHand;
}
