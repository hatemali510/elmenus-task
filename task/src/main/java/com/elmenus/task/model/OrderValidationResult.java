package com.elmenus.task.model;

import java.util.List;

public class OrderValidationResult {

    private Boolean valid;
    private List<RuleResult> notPassedRules;
    private List<RuleResult> passedRules;

    public List<RuleResult> getPassedRule() {
        return passedRules;
    }

    public void setPassedRule(List<RuleResult> passedRule) {
        this.passedRules = passedRule;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<RuleResult> getNotPassedRules() {
        return notPassedRules;
    }

    public void setNotPassedRules(List<RuleResult> notPassedRules) {
        this.notPassedRules = notPassedRules;
    }
}
