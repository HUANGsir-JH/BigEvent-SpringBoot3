package org.huang.bigevent.exceptionHandler;

import org.huang.bigevent.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return Result.builder().code(1).message(e.getClass()+" "+e.getMessage()).build();
    }
}
