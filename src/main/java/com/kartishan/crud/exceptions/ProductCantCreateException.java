package com.kartishan.crud.exceptions;

/**
 * Исключение ProductCantCreateException
 */
public class ProductCantCreateException extends RuntimeException{
    /**
     * Конструктор
     * @param message - сообщение об ошибке
     */
    public ProductCantCreateException(String message){
        super(message);
    }
}
