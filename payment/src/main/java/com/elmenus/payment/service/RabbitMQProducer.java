package com.elmenus.payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);
    /**
     * The amqp template.
     */
    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
     * The exchange.
     */
    @Value("${rabbitmq.exchange}")
    private String exchange;

    /**
     * Produce message to given RabbitMQ queue.
     *
     * @param routingKey the RabbitMQ routing key
     * @param msg        the message
     */
    public void produceMsg(String routingKey, String msg) {

        amqpTemplate.convertAndSend(exchange, routingKey, msg);

        logger.info("Send msg {} {} = " ,routingKey, msg);
    }

}