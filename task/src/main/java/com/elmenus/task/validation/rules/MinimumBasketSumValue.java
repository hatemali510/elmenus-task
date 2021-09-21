package com.elmenus.task.validation.rules;

import com.elmenus.task.model.Item;
import com.elmenus.task.model.Order;
import com.elmenus.task.model.Rule;
import com.elmenus.task.model.RuleResult;
import org.springframework.stereotype.Service;


@Service("minimum-basket")
public class MinimumBasketSumValue implements RuleValidation{
    @Override
    public RuleResult applyValidation(Rule rule, Order order) {
        Double minimumBasketPricesValue= Double.valueOf((String) rule.getConditionValue());
        Double sum = order.getOrderItems().stream().mapToDouble(Item::getPrice).sum();
        if (sum<minimumBasketPricesValue){
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
