package io.swagger.petstore;

import io.swagger.petstore.controller.StoreController;
import io.swagger.petstore.model.Order;
import io.swagger.petstore.model.status.OrderStatus;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StoreControllerTest {

    private final StoreController storeController = new StoreController();

    @Test
    public void testFindPurchaseOrderById() {
        Order order = storeController.getOrderById(5L, HttpStatus.SC_OK).as(Order.class);

        assertThat(order.getId(), equalTo(5L));
        assertThat(order.getPetId(), equalTo(5L));
        assertThat(order.getQuantity(), equalTo(5));
        assertThat(order.getStatus(), equalTo(OrderStatus.placed));
        assertThat(order.getComplete(), equalTo(Boolean.TRUE));
    }
}
