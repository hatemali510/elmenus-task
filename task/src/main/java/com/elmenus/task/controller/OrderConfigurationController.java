package com.elmenus.task.controller;


import com.elmenus.task.model.OrderConfiguration;
import com.elmenus.task.model.Rule;
import com.elmenus.task.service.OrderConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/configuration")
public class OrderConfigurationController {


    @Autowired
    OrderConfigurationService orderConfigurationService;

    @GetMapping("/configuration-rules/get/order-type/{orderType}")
    public ResponseEntity<List<Rule>> getAllConfigurationRules(@PathVariable String orderType){
        return ResponseEntity.ok(orderConfigurationService.getOrderValidationRule(orderType));
    }


    @PostMapping("/add")
    public ResponseEntity addOrderConfiguration(@RequestBody OrderConfiguration orderConfiguration){
        orderConfigurationService.addOrderConfiguration(orderConfiguration);
        return ResponseEntity.ok("done");
    }

    @PutMapping("/edit/rule/type/{orderType}")
    public ResponseEntity updateOrderConfigurationRule(@RequestBody Rule rule,@PathVariable String orderType){
        return ResponseEntity.ok(orderConfigurationService.updateConfigurationRule(rule,orderType));
    }


}
