package com.kartishan.crud.exceptions;

/**
 * Исключение ProductCantDeleteException
 */
public class ProductCantDeleteException extends RuntimeException{
    /**
     * Конструктор
     * @param message - сообщение об ошибке
     */
    public ProductCantDeleteException(String message){
        super(message);
    }
}
