package com.elmenus.payment.service;

import com.elmenus.payment.model.Order;
import com.elmenus.payment.model.PaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {


    @Autowired
    StripeService stripeService;

    @Value("${stripe.service.token}")
    private String token;

    @Value("${payment.queue}")
    private String paymentQueue;


    @Autowired
    RabbitMQProducer rabbitMQProducer;
    public void createPayment( Order order) throws JsonProcessingException {
        PaymentResponse paymentResponse=new PaymentResponse();
        String chargeId=stripeService.createCharge(order.getOrderPayment().getVisaNumber(),token,order.getTotalAmount().intValue());
        if (chargeId!=null){
            paymentResponse.setPaymentStatus("success");
            paymentResponse.setTransactionId(chargeId);
        }else {
            paymentResponse.setPaymentStatus("FAILED");
        }
        // produce the message to task microservice
        rabbitMQProducer.produceMsg(paymentQueue,new ObjectMapper().writeValueAsString(paymentResponse));
    }
}
