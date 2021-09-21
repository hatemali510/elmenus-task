package com.elmenus.task.service;

import com.elmenus.task.constant.AppConstant;
import com.elmenus.task.integration.PaymentGatewayService;
import com.elmenus.task.model.*;
import com.elmenus.task.validation.OrderValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderValidation orderValidation;

    @Autowired
    PaymentGatewayService paymentGatewayService;
    @Autowired
    ItemService itemService;

    private OrderValidationResult validateOrder(Order order) {
        OrderValidationResult orderValidationResult = orderValidation.validate(order);
        return orderValidationResult;
    }

    @Override
    public ResponseEntity CheckOutOrder(Order order, String orderTransactionId) {

        try {
            logger.info(" ======== check out order with order transaction id : {} is under processing =======", orderTransactionId);
            order.setOrderItems(getOrderItemsDetails(order));
            order.setOrderTransactionId(orderTransactionId);
            OrderValidationResult orderValidationResult = validateOrder(order);
            logger.info("=========validation order is finished with result : {}========", orderValidationResult);
            if (orderValidationResult != null) {
                if (orderValidationResult.getValid()) {
                    logger.info("=========order is valid and will integrate with payment gateway to pay the order========");
                    /*
                    =======================================================
                    asynch call to rabbit mq pay the order and return the payment status later
                    */
                    payOrder(order);
                    /*
                    =======================================================
                     */

                    order.setOrderStatus(AppConstant.SUCCESS_VALIDATION_ORDER_STATUS);
                    return ResponseEntity.ok(order);
                } else {
                    List<String> errorsMessages = new ArrayList<>();
                    logger.warn("=========order is not valid========");
                    // save order validation in database
                    // skip adding in the database because not need in the task .
                    List<RuleResult> notPassedRules = orderValidationResult.getNotPassedRules();
                    order.setValidationErrors(notPassedRules.stream().map(RuleResult::getRuleResultMessage).collect(Collectors.toList()));
                    for (RuleResult notPassedRule : notPassedRules) {
                        logger.error("order is not valid because validation of rule : {} not passed with status :{} and errorCode : {}", notPassedRule.getRule().getName(), notPassedRule.getRule().getErrorStatus(), notPassedRule.getRule().getErrorStatusCode());
                        errorsMessages.add(notPassedRule.getRuleResultMessage());
                    }
                    order.setOrderStatus(AppConstant.FAILED_ORDER_STATUS);
                    order.setPaymentStatus(AppConstant.NOT_STARTED_PAYMENT);
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(order);
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("order with transaction Id : " + orderTransactionId + " validation process failed , please send order again ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("general error happen please reorder again ...");
        }


    }

    private List<Item> getOrderItemsDetails(Order order) {
        List<Item> itemDetails = new ArrayList<>();
        if (order.getOrderItems() != null) {
            for (Item item : order.getOrderItems()) {
                itemDetails.add(itemService.getItemByName(item.getName()));
            }
        }
        return itemDetails;
    }

    private void payOrder(Order order) {
        paymentGatewayService.payOrder(order);
    }

    private Boolean validatePayment(OrderPayment orderPayment, String orderTransactionId) {
        if (orderPayment.getPaymentStatus().equalsIgnoreCase(AppConstant.SUCCESS_PAYMENT_STATUS)) {
            // log successfully payment in order details in the database
            // get order details with order transaction id .
            logger.info("order payment status : {} for order transaction id is : {} ", orderPayment.getPaymentStatus(), orderTransactionId);
            return true;
        } else {
            // log failed payment in database .
            // get order details with order transaction id .
            logger.warn("order payment status : {} for order transaction id is : {} ", orderPayment.getPaymentStatus(), orderTransactionId);
            return false;
        }
    }
}
