package com.elmenus.task.service;

import com.elmenus.task.model.Order;
import org.springframework.http.ResponseEntity;

public interface OrderService {


    ResponseEntity CheckOutOrder(Order order,String orderTransactionId);
}
