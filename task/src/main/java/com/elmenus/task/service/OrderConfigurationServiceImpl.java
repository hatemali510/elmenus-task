package com.elmenus.task.service;

import com.elmenus.task.model.OrderConfiguration;
import com.elmenus.task.model.Rule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Service
public class OrderConfigurationServiceImpl implements OrderConfigurationService{

    private static  Map<String, OrderConfiguration> orderConfigurationsMap= Collections.synchronizedMap(new HashMap<>());
    private static Logger logger= LoggerFactory.getLogger(OrderConfigurationServiceImpl.class);


    @Override
    public List<Rule> getOrderValidationRule(String orderType) {
        OrderConfiguration orderValidationConfiguration=orderConfigurationsMap.get(orderType);
        return orderValidationConfiguration.getOrderValidationRules();
    }



    @Override
    public void addOrderConfiguration(OrderConfiguration orderConfiguration){
        orderConfigurationsMap.put(orderConfiguration.getOrderType(),orderConfiguration);
    }

    @Override
    public OrderConfiguration updateConfigurationRule(Rule rule, String orderType) {
        OrderConfiguration orderConfiguration=orderConfigurationsMap.get(orderType);
        if (orderConfiguration!=null){
            List<Rule> configurationRules=orderConfiguration.getOrderValidationRules();
            for (Rule configurationRule : configurationRules) {
                if (configurationRule.getName().equalsIgnoreCase(rule.getName())) {
                    configurationRule.setConditionValue(rule.getConditionValue());
                    configurationRule.setErrorFailedMessage(rule.getErrorFailedMessage());
                    configurationRule.setErrorStatus(rule.getErrorStatus());
                    configurationRule.setErrorStatusCode(rule.getErrorStatusCode());
                }
            }
            orderConfiguration.setOrderValidationRules(configurationRules);
            return orderConfiguration;
        }else {
            return null;
        }
    }


    @Override
    public void refreshOrderValidationConfigurations() {
        loadAllOrderConfiguration();
    }


    @PostConstruct
    private void loadAllOrderConfiguration(){
        // assume getting from database
        // at this task will get from orderConfigurations.json file
        TypeReference<List<OrderConfiguration>> typeReference = new TypeReference<List<OrderConfiguration>>() {
        };

        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/order_configurations.json");
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
            List<OrderConfiguration> configurationList = mapper.readValue(inputStream, typeReference);
            for (OrderConfiguration orderValidationConfiguration : configurationList) {
                orderConfigurationsMap.put(orderValidationConfiguration.getOrderType(),orderValidationConfiguration);
            }
            logger.info("configurations is added ");
        } catch (IOException e) {
            logger.error("Unable to add configurations : {}", e.getMessage());
        }

    }
}
