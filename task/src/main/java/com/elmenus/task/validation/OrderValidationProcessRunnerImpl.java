package com.elmenus.task.validation;

import com.elmenus.task.model.Order;
import com.elmenus.task.model.Rule;
import com.elmenus.task.model.RuleResult;
import com.elmenus.task.validation.rules.RuleValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class OrderValidationProcessRunnerImpl implements ValidationProcessRunner {
    @Autowired
    private Map<String, RuleValidation> validationRuleMap;

    @Override
    public RuleResult runRuleValidation(Object... objects) {
        Rule rule= (Rule) objects[0];
        Order order= (Order) objects[1];
        RuleResult ruleResult=validationRuleMap.get(rule.getName()).applyValidation(rule,order);
        return ruleResult;
    }

}
