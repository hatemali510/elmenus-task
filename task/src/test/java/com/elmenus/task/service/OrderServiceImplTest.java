package com.elmenus.task.service;

import com.elmenus.task.integration.PaymentGatewayService;
import com.elmenus.task.model.Item;
import com.elmenus.task.model.Order;
import com.elmenus.task.model.OrderPayment;
import com.elmenus.task.model.OrderValidationResult;
import com.elmenus.task.validation.OrderValidation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    private OrderServiceImpl orderServiceImplUnderTest;

    @Before
    public void setUp() {
        orderServiceImplUnderTest = new OrderServiceImpl();
        orderServiceImplUnderTest.orderValidation = mock(OrderValidation.class);
        orderServiceImplUnderTest.paymentGatewayService = mock(PaymentGatewayService.class);
        orderServiceImplUnderTest.itemService = mock(ItemService.class);
    }

    @Test
    public void testCheckOutOrder() {
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

        // Configure ItemService.getItemByName(...).
        final Item item1 = new Item();
        item1.setStock(0.0);
        item1.setName("name");
        item1.setPrice(0.0);
        when(orderServiceImplUnderTest.itemService.getItemByName("itemName")).thenReturn(item1);

        when(orderServiceImplUnderTest.orderValidation.validate(any(Order.class))).thenReturn(new OrderValidationResult());

        // Run the test
        final ResponseEntity result = orderServiceImplUnderTest.CheckOutOrder(order, "orderTransactionId");

        // Verify the results
        verify(orderServiceImplUnderTest.paymentGatewayService).payOrder(any(Order.class));
    }
}
