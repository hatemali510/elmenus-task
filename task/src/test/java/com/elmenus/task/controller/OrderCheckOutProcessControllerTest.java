package com.elmenus.task.controller;

import com.elmenus.task.model.Item;
import com.elmenus.task.model.Order;
import com.elmenus.task.model.OrderPayment;
import com.elmenus.task.model.OrderValidationResult;
import com.elmenus.task.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderCheckOutProcessControllerTest {

    private OrderCheckOutProcessController orderCheckOutProcessControllerUnderTest;

    @Before
    public void setUp() {
        orderCheckOutProcessControllerUnderTest = new OrderCheckOutProcessController();
        orderCheckOutProcessControllerUnderTest.orderService = mock(OrderService.class);
    }

    @Test
    public void testCheckoutOrder() {
        // Setup
        final Order order = new Order();
        order.setOrderPayment(new OrderPayment("paymentStatus", "orderTransactionId"));
        order.setOrderTransactionId("orderTransactionId");
        order.setValidationErrors(Arrays.asList("value"));
        order.setPaymentStatus("paymentStatus");
        order.setOrderStatus("orderStatus");
        order.setOrderValidationResult(new OrderValidationResult());
        order.setOrderType("orderType");
        final Item item = new Item();
        item.setStock(0.0);
        item.setName("name");
        item.setPrice(0.0);
        order.setOrderItems(Arrays.asList(item));
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        when(orderCheckOutProcessControllerUnderTest.orderService.CheckOutOrder(any(Order.class), eq("orderTransactionId"))).thenReturn(new ResponseEntity<>("body", HttpStatus.CONTINUE));

        // Run the test
        final ResponseEntity result = orderCheckOutProcessControllerUnderTest.checkoutOrder(order, "orderTransactionId");

        // Verify the results
    }


}
