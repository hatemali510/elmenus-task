package com.elmenus.task.validation;

import com.elmenus.task.model.Order;
import com.elmenus.task.model.OrderValidationResult;

 abstract class ValidationProcess {

     abstract OrderValidationResult validate(Order order) ;
 }
