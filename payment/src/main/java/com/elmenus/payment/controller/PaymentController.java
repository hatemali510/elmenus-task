package com.elmenus.payment.controller;


import com.elmenus.payment.model.Order;
import com.elmenus.payment.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments/")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("create/{transactionId}")
    public ResponseEntity createPayment(@RequestBody Order order, @PathVariable String transactionId) throws JsonProcessingException {
        paymentService.createPayment(order);
        return ResponseEntity.ok("payment under processing ");
    }
}
