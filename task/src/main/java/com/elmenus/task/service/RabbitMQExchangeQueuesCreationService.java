package com.elmenus.task.service;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitMQExchangeQueuesCreationService {


    @Autowired
    RabbitAdmin rabbitAdmin;


    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${payment.queue}")
    private String paymentQueue;

    /**
     * The rabbit MQ host.
     */
    @Value("${integration.rabbitmq.host}")
    private String integerationRabbitMQHost;

    /**
     * The rabbit MQ port.
     */
    @Value("${integration.rabbitmq.port}")
    private Integer integerationRabbitMQPort;

    /**
     * The rabbit MQ port.
     */


    @Value("${integration.rabbitmq.virtual-host}")
    private String integerationRabbitMQVHost;

    /**
     * The rabbit MQ host.
     */
    @Value("${integration.rabbitmq.username}")
    private String integerationRabbitMQUsername;

    /**
     * The rabbit MQ port.
     */
    @Value("${integration.rabbitmq.password}")
    private String integerationRabbitMQPassword;

    /**
     * The integration queue name.
     */
    @Value("${integration.queue.name}")
    private String integrationQueueName;



    /**
     * The rabbit MQ host.
     */
    @Value("${spring.rabbitmq.host}")
    private String rabbitMQHost;

    /**
     * The rabbit MQ port.
     */
    @Value("${spring.rabbitmq.port}")
    private Integer rabbitMQPort;

    /**
     * The rabbit MQ port.
     */
    @Value("${spring.rabbitmq.virtual-host}")
    private String rabbitMQVHost;

    /**
     * The rabbit MQ host.
     */
    @Value("${spring.rabbitmq.username}")
    private String rabbitMQUsername;


    @Value("${spring.rabbitmq.password}")
    private String rabbitMQPassword;

    private DirectExchange exchange;

    @PostConstruct
    private void init() {

        exchange = new DirectExchange(exchangeName);

        rabbitAdmin.declareExchange(exchange);
        // declare delay exchange
        declareQueue(paymentQueue);



    }

    /**
     * Connection factory.
     *
     * @return the connection factory
     */
    @Bean
    @Primary
    public ConnectionFactory connectionFactory() {
        SimpleRoutingConnectionFactory connectionFactory = new SimpleRoutingConnectionFactory();
        Map<Object, ConnectionFactory> targetConnectionFactories = new HashMap<>();
        targetConnectionFactories.put(constructQueueName(paymentQueue),acsConnectionFactory());
        connectionFactory.setTargetConnectionFactories(targetConnectionFactories);
        return connectionFactory;
    }

    /**
     * Construct queue name.
     *
     * @param queueName the queue name
     * @return the string
     */
    private String constructQueueName(String queueName) {
        return String.format("[%s]", queueName);
    }

    /**
     * Hlp connection factory.
     *
     * @return the connection factory
     */
    @Bean
    public ConnectionFactory acsConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(rabbitMQHost, rabbitMQPort);
        factory.setVirtualHost(rabbitMQVHost);
        factory.setUsername(rabbitMQUsername);
        factory.setPassword(rabbitMQPassword);
        return factory;
    }

    /**
     * Integeration connection factory.
     *
     * @return the connection factory
     */
    @Bean
    public ConnectionFactory integerationConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(integerationRabbitMQHost, integerationRabbitMQPort);
        connectionFactory.setVirtualHost(integerationRabbitMQVHost);
        connectionFactory.setUsername(integerationRabbitMQUsername);
        connectionFactory.setPassword(integerationRabbitMQPassword);
        return connectionFactory;
    }

    /**
     * Rabbit template.
     *
     * @return the rabbit template
     */
    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(acsConnectionFactory());
    }


    /**
     * Define queues and binding them to exchanges.
     *
     * @param queueName the queue name
     */
    private void declareQueue(String queueName) {

        Queue queue = new Queue(queueName);

        rabbitAdmin.declareQueue(queue);

        Binding binding = BindingBuilder.bind(queue).to(exchange).with(queue.getName());

        rabbitAdmin.declareBinding(binding);
    }


    @Bean
    public RabbitAdmin rabbitAdmin() {

        return new RabbitAdmin(acsConnectionFactory());
    }



}