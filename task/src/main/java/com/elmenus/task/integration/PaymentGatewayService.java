package com.elmenus.task.integration;

import com.elmenus.task.model.Order;
import com.elmenus.task.model.OrderPayment;

public interface PaymentGatewayService {

    void payOrder(Order order);
}
