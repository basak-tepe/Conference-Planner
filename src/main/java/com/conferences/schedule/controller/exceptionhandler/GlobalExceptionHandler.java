package com.conferences.schedule.controller.exceptionhandler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SimpleGlobalException.class)
    protected ResponseEntity<?> handleGlobalException(SimpleGlobalException simpleGlobalException) {
        log.error("Event Service Exception! Error Message:" + simpleGlobalException.getMessage(), simpleGlobalException);
        return ResponseEntity.badRequest().body(simpleGlobalException.toErrorResponse());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<?> handleException(Exception e) {
        log.error("Unhandled Exception! Error Message: " + e.getMessage(), e);
        return ResponseEntity.badRequest().body("Exception occur inside Event API " + e);
    }

}
