package com.elmenus.task.model;

public class Rule {

    private String name;
    private String errorStatus;
    private String errorStatusCode;
    private String errorFailedMessage;
    private Object conditionValue;

    public Object getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(Object conditionValue) {
        this.conditionValue = conditionValue;
    }

    public String getErrorFailedMessage() {
        return errorFailedMessage;
    }

    public void setErrorFailedMessage(String errorFailedMessage) {
        this.errorFailedMessage = errorFailedMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorStatusCode() {
        return errorStatusCode;
    }

    public void setErrorStatusCode(String errorStatusCode) {
        this.errorStatusCode = errorStatusCode;
    }
}
