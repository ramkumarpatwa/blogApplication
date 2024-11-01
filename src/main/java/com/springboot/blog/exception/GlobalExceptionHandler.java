package com.springboot.blog.exception;

import com.springboot.blog.dto.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice//used to handle exceptions globally
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    //just like writing REST API, here also we will provide HTTP response

    @ExceptionHandler(ResourceNotFoundException.class)//used to handle specific exception and send custom response to client
    ResponseEntity<ErrorDetails> hanleResourceNotFoundException(ResourceNotFoundException exception,
                                                                WebRequest webRequest){//in para1 pass Exception that we are handling//in para2 we are going to send some details from Web Request to the client
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)//to handle specific exception
    ResponseEntity<ErrorDetails> hanleBlogException(BlogApiException exception,
                                                                WebRequest webRequest){//in para1 pass Exception that we are handling//in para2 we are going to send some details from Web Request to the client
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //global exceptions handler
    //all exceptions apart from above two will be handled here
    @ExceptionHandler(Exception.class)//to handle specific exception
    ResponseEntity<ErrorDetails> hanleGlobalException(Exception exception,
                                                    WebRequest webRequest){//in para1 pass Exception that we are handling//in para2 we are going to send some details from Web Request to the client
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //below is to handle Bean validation, if we want to show  message only
    //extend GlobalExceptionHandler with ResponseEntityExceptionHandler and override method MethodArgumentNotValidException() like below
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        // Extract the specific validation error message
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();

        // Create custom error details with the simplified message
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, "Validation failed");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
