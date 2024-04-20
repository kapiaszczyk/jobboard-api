package com.kapia.jobboard.api.exceptionhandlers;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackages = "com.kapia.jobboard.api.controller")
public class ConstraintViolationException extends Exception {


}
