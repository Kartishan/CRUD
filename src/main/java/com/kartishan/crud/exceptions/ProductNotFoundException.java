package com.kartishan.crud.exceptions;

/**
 * Исключение ProductNotFoundException
 */
public class ProductNotFoundException extends RuntimeException{
    /**
     * Конструктор
     * @param message - сообщение об ошибке
     */
    public ProductNotFoundException(String message){
        super(message);
    }
}
