package com.elmenus.payment.service;


import com.elmenus.payment.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PaymentConsumers {

    @Autowired
    PaymentService paymentService;

    @RabbitListener(queues = "${acs.loan.status.update.queue.name}")
    public void receiveLoanActivationMessage(String message) throws IOException {
        Order order = new ObjectMapper().readValue(message, Order.class);
        try {
            paymentService.createPayment(order);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
