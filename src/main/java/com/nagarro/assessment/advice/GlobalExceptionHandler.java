package com.nagarro.assessment.advice;

import com.nagarro.assessment.constants.ErrorMessages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.error(errors);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Map<String,String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error(ex);
        return new ResponseEntity<>(Map.of(ErrorMessages.ERROR,ErrorMessages.INVALID_REQUEST_BODY), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class,RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleIllegalArgumentException(Exception ex) {
        log.error(ex);
        return new ResponseEntity<>(Map.of(ErrorMessages.ERROR,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<Map<String,String>> handleAllException(Exception ex) {
        log.error(ex);
        return new ResponseEntity<>(Map.of(ErrorMessages.ERROR,ErrorMessages.UNEXPECTED), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
