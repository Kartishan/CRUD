package com.kartishan.crud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;


/**
 * Глобальный обработчик исключений
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Метод для обработки исключения ProductNotFoundException
     * @param ex Исключение ProductNotFoundException
     * @return Возвращает ResponseEntity с ошибкой
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
    /**
     * Метод для обработки исключения ProductCantDeleteException
     * @param ex Исключение ProductCantDeleteException
     * @return Возвращает ResponseEntity с ошибкой
     */
    @ExceptionHandler(ProductCantDeleteException.class)
    public ResponseEntity<ErrorResponse> handleProductCantDeleteException(ProductCantDeleteException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    /**
     * Метод для обработки исключения ProductCantCreateException
     * @param ex Исключение ProductCantCreateException
     * @return Возвращает ResponseEntity с ошибкой
     */
    @ExceptionHandler(ProductCantCreateException.class)
    public ResponseEntity<ErrorResponse> handleProductCantCreateException(ProductCantCreateException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    /**
     * Метод для обработки исключения ProductCantUpdateException
     * @param ex Исключение ProductCantUpdateException
     * @return Возвращает ResponseEntity с ошибкой
     */
    @ExceptionHandler(ProductCantUpdateException.class)
    public ResponseEntity<ErrorResponse> handleProductCantUpdateException(ProductCantUpdateException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    /**
     * Метод для обработки исключения ProductsCantGetsException
     * @param ex Исключение ProductsCantGetsException
     * @return Возвращает ResponseEntity с ошибкой
     */
    @ExceptionHandler(ProductsCantGetsException.class)
    public ResponseEntity<ErrorResponse> ProductsCantGetsException(ProductsCantGetsException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(new Date());


        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    /**
     * Метод для обработки исключения ProductDuplicateException
     * @param ex Исключение ProductDuplicateException
     * @return Возвращает ResponseEntity с ошибкой
     */
    @ExceptionHandler(ProductDuplicateException.class)
    public ResponseEntity<ErrorResponse> ProductDuplicateException(ProductDuplicateException ex){
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatusCode(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(new Date());


        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }
}
