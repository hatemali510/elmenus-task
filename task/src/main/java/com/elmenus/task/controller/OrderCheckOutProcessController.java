package com.elmenus.task.controller;


import com.elmenus.task.model.Order;
import com.elmenus.task.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order/")
public class OrderCheckOutProcessController {

    @Autowired
    OrderService orderService;

    @PostMapping("checkout/{orderTransactionId}")
    public ResponseEntity checkoutOrder(@RequestBody Order order, @PathVariable String orderTransactionId){
        return  orderService.CheckOutOrder(order,orderTransactionId);
    }
}
