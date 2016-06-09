package org.rest.service.sudoku.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * Created by zlobina on 16.03.16.
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(UnsupportedOperationException.class)
    public @ResponseBody
    ErrorModel sendAccessError( UnsupportedOperationException ex) {
        logger.error("ACCESS ERROR", ex);
        return new ErrorModel(-1, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ErrorModel sendError(Exception ex) {
        logger.error("UNRECOGNIZED ERROR", ex);
        return new ErrorModel(-2, ex.getMessage());
    }


    public class ErrorModel{
        private final int errorCode;
        private final String errorMsg;

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public ErrorModel(int errorCode, String errorMsg){
            this.errorCode = errorCode;
            this.errorMsg = errorMsg;
        }
    }

}
