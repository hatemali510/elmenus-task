package com.elmenus.task.validation;

import com.elmenus.task.model.Order;
import com.elmenus.task.model.Rule;
import com.elmenus.task.model.RuleResult;

import java.util.List;

public interface ValidationProcessRunner {

    RuleResult runRuleValidation(Object... objects);
}
