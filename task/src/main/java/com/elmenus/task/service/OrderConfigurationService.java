package com.elmenus.task.service;

import com.elmenus.task.model.OrderConfiguration;
import com.elmenus.task.model.Rule;

import java.util.List;

public interface OrderConfigurationService {

    List<Rule> getOrderValidationRule(String orderType);
    void refreshOrderValidationConfigurations();
    void addOrderConfiguration(OrderConfiguration orderConfiguration);
    OrderConfiguration updateConfigurationRule(Rule rule,String orderType);
}
