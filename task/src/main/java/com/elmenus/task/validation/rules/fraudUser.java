package com.elmenus.task.validation.rules;

import com.elmenus.task.model.Item;
import com.elmenus.task.model.Order;
import com.elmenus.task.model.Rule;
import com.elmenus.task.model.RuleResult;
import com.elmenus.task.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("fraud-user")
public class fraudUser implements RuleValidation {

    @Autowired
    ItemService itemService;

    @Override
    public RuleResult applyValidation(Rule rule, Order order) {
        Double ruleConditionValue= Double.parseDouble((String) rule.getConditionValue());
        Double sum = order.getOrderItems().stream().mapToDouble(Item::getPrice).sum();
        if (sum>ruleConditionValue){
            return new RuleResult.RuleResultBuilder(order.getOrderTransactionId(),rule,false,
                    generateRuleFailedValidationMessage(rule)).build();
        }else {
            return new RuleResult.RuleResultBuilder(order.getOrderTransactionId(),rule,true,
                    null).build();
        }
    }
    private String generateRuleFailedValidationMessage(Rule rule){
        return rule.getErrorFailedMessage();
    }

}
