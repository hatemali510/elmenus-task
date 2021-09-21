package com.elmenus.task.validation.rules;

import com.elmenus.task.model.Item;
import com.elmenus.task.model.Order;
import com.elmenus.task.model.Rule;
import com.elmenus.task.model.RuleResult;
import com.elmenus.task.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("item-availability")
public class ItemAvailability implements RuleValidation{
    private static Logger logger = LoggerFactory.getLogger(ItemAvailability.class);

    @Autowired
    ItemService itemService;
    @Override
    public RuleResult applyValidation(Rule rule, Order order) {
        List<Item> orderItems=order.getOrderItems();
        if (orderItems!=null){
            for (Item item:orderItems){
                Boolean available=getItemAvailability(item);
                if (available!=null && !available){
                    // builder design pattern
                    return new RuleResult.RuleResultBuilder(order.getOrderTransactionId(),rule,false,
                            generateRuleFailedValidationMessage(rule,item)).build();
                }
            }
        }
        return new RuleResult.RuleResultBuilder(order.getOrderTransactionId(),rule,true,
                null).build();
    }

    private Boolean getItemAvailability(Item item) {
        return itemService.getItemAvailability(item.getName());
    }

    private String generateRuleFailedValidationMessage(Rule rule,Item item){
        logger.info("rule failed message : {}",rule.getErrorFailedMessage());
       return String.format(rule.getErrorFailedMessage(), item.getName());
    }
}
