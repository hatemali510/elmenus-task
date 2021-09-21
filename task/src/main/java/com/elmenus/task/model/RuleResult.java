package com.elmenus.task.model;

public class RuleResult {

    private String orderTransactionId;
    private Rule rule;
    private Boolean passed;
    private String ruleResultMessage;

    private RuleResult(RuleResultBuilder ruleResultBuilder){
        this.orderTransactionId=ruleResultBuilder.orderTransactionId;
        this.passed=ruleResultBuilder.passed;
        this.rule=ruleResultBuilder.rule;
        this.ruleResultMessage=ruleResultBuilder.ruleResultMessage;
    }
    public String getRuleResultMessage() {
        return ruleResultMessage;
    }
    public String getOrderTransactionId() {
        return orderTransactionId;
    }
    public Rule getRule() {
        return rule;
    }

    public Boolean getPassed() {
        return passed;
    }

    /*
    builder design pattern
     */
    public static class RuleResultBuilder{
        private final String orderTransactionId;
        private final Rule rule;
        private final Boolean passed;
        private final String ruleResultMessage;

        public RuleResultBuilder(String orderTransactionId,Rule rule,Boolean passed,String ruleResultMessage){
            this.orderTransactionId=orderTransactionId;
            this.rule=rule;
            this.passed=passed;
            this.ruleResultMessage=ruleResultMessage;
        }

        public RuleResult build(){
            RuleResult ruleResult=new RuleResult(this);
            return ruleResult;
        }
    }
}


