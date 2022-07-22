package jpabook.jpashop.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class SowonExceptionHandler {


    @ExceptionHandler(SowonException.class)
    public ResponseEntity sowonException(SowonException e, HttpServletRequest request) {
        return response(HttpStatus.valueOf(e.getHttpStatusCode()), e.getCode(), e.getMessage());
    }

    private ResponseEntity response(HttpStatus httpStatus, String code, String message) {
        return new ResponseEntity<>(Error.builder().error(Error.Value.builder().code(code).message(message).build()).build(), httpStatus);
    }
}
