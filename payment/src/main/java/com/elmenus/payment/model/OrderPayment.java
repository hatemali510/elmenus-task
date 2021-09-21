package com.elmenus.payment.model;

public class OrderPayment {

    private String paymentStatus;
    private String orderTransactionId;
    private String paymentChannel;
    private String visaNumber;
    private String cvv;
    private String visaExpireDate;

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getVisaNumber() {
        return visaNumber;
    }

    public void setVisaNumber(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getVisaExpireDate() {
        return visaExpireDate;
    }

    public void setVisaExpireDate(String visaExpireDate) {
        this.visaExpireDate = visaExpireDate;
    }

    public OrderPayment(String paymentStatus, String orderTransactionId){
        this.paymentStatus=paymentStatus;
        this.orderTransactionId=orderTransactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }



    public String getOrderTransactionId() {
        return orderTransactionId;
    }

    public void setOrderTransactionId(String orderTransactionId) {
        this.orderTransactionId = orderTransactionId;
    }
}
