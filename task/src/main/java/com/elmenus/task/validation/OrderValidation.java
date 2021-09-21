package com.elmenus.task.validation;

import com.elmenus.task.model.Order;
import com.elmenus.task.model.OrderValidationResult;
import com.elmenus.task.model.Rule;
import com.elmenus.task.model.RuleResult;
import com.elmenus.task.service.OrderConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderValidation extends ValidationProcess{


    @Autowired
    OrderValidationProcessRunnerImpl orderValidationProcessRunner;



    @Autowired
    OrderConfigurationService orderConfigurationService;



    @Override
    public OrderValidationResult validate(Order order) {
        OrderValidationResult orderValidationResult =new OrderValidationResult();
        List<Rule> orderRule=orderConfigurationService.getOrderValidationRule(order.getOrderType());
        List<RuleResult> ruleResults=new ArrayList<>();
        for (Rule rule: orderRule) {
             ruleResults.add(orderValidationProcessRunner.runRuleValidation(rule, order));
        }
        List<RuleResult> passedRules=new ArrayList<>();
        List<RuleResult> notPassedRules=new ArrayList<>();
        for (RuleResult ruleResult : ruleResults){
            if (!ruleResult.getPassed()){
                notPassedRules.add(ruleResult);
            }else {
                passedRules.add(ruleResult);
            }
        }
        if (notPassedRules.size()>0){
            orderValidationResult.setValid(false);
        }else {
            orderValidationResult.setValid(true);
        }
        orderValidationResult.setNotPassedRules(notPassedRules);
        orderValidationResult.setPassedRule(passedRules);
        return orderValidationResult;
    }


}
