package com.kartishan.crud.exceptions;

/**
 * Исключение ProductsCantGetsException
 */
public class ProductsCantGetsException extends RuntimeException {
    /**
     * Конструктор
     * @param message - сообщение об ошибке
     */
    public ProductsCantGetsException(String message){
        super(message);
    }
}
