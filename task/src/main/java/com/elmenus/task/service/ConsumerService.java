package com.elmenus.task.service;


import com.elmenus.task.model.PaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConsumerService {


    @RabbitListener(queues = "${acs.loan.status.update.queue.name}")
    public void receiveLoanActivationMessage(String message) throws IOException {
        PaymentResponse paymentResponse = new ObjectMapper().readValue(message, PaymentResponse.class);
        try {
            // accept the payment response message with transaction id and update the oder in the database that the order is payed
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
