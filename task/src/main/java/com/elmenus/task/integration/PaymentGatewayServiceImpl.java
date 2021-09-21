package com.elmenus.task.integration;

import com.elmenus.task.constant.AppConstant;
import com.elmenus.task.model.Order;
import com.elmenus.task.model.OrderPayment;
import com.elmenus.task.model.PaymentResponse;
import com.elmenus.task.service.RabbitMQProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    RabbitMQProducer rabbitMQProducer;

    @Value("${payment.queue}")
    private String paymentQueue;

    @Override
    public void payOrder(Order order) {
        try {

            rabbitMQProducer.produceMsg(paymentQueue,objectMapper.writeValueAsString(order));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
