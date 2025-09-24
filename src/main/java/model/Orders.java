package model;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders {
    private String orderID ;
    private LocalDate orderDate;
    private String customerID ;
}
