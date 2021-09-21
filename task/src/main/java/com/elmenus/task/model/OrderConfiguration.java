package com.elmenus.task.model;

import java.util.List;

public class OrderConfiguration {

    private String orderType;
    private List<Rule> orderValidationRules;
    private int maximumFailedNumber;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public List<Rule> getOrderValidationRules() {
        return orderValidationRules;
    }

    public void setOrderValidationRules(List<Rule> orderValidationRules) {
        this.orderValidationRules = orderValidationRules;
    }

    public int getMaximumFailedNumber() {
        return maximumFailedNumber;
    }

    public void setMaximumFailedNumber(int maximumFailedNumber) {
        this.maximumFailedNumber = maximumFailedNumber;
    }
}
