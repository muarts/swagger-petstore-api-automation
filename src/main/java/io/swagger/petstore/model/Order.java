package io.swagger.petstore.model;

import io.swagger.petstore.model.status.OrderStatus;

public class Order {
    private Long id;
    private Long petId;
    private Integer quantity;
    private String shipDate;
    private OrderStatus status;
    private Boolean complete;
}
