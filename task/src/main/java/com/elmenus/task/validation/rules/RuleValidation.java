package com.elmenus.task.validation.rules;

import com.elmenus.task.model.Order;
import com.elmenus.task.model.Rule;
import com.elmenus.task.model.RuleResult;

public interface RuleValidation {

    RuleResult applyValidation(Rule rule, Order order);
}
