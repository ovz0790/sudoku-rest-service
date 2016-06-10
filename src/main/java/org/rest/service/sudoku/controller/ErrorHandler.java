package org.rest.service.sudoku.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.NestedServletException;

import javax.xml.bind.ValidationException;
import java.io.IOException;

/**
 * Created by zlobina on 16.03.16.
 */
@ControllerAdvice(basePackageClasses = ErrorHandler.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler({ValidationException.class,
                    NestedServletException.class})
    public @ResponseBody
    ResponseEntity<ErrorModel> sendValidateError(ValidationException ex) {
        logger.error("Invalid input data", ex);
        return new ResponseEntity<>(new ErrorModel(-1, ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IOException.class)
    public @ResponseBody
    ErrorModel sendIOError(IOException ex) {
        logger.error("Board copy error", ex);
        return new ErrorModel(-100, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ErrorModel sendError(Exception ex) {
        logger.error("UNRECOGNIZED ERROR", ex);
        return new ErrorModel(-1000, ex.getMessage());
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
