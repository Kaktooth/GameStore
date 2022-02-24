package com.store.gamestore.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.sql.SQLInvalidAuthorizationSpecException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@ControllerAdvice
public class AdviceExceptionHandler {

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> invalidAuthentication(SQLInvalidAuthorizationSpecException ex) {
        log.error("InvalidAuthenticationException...", ex);
        return status(UNAUTHORIZED).build();
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity<Object> invalidAuthentication(EmptyResultDataAccessException ex) {
        log.error("Empty result...", ex);
        return status(HttpStatus.NO_CONTENT).build();
    }
}
