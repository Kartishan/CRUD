package com.kartishan.crud.exceptions;

/**
 * Исключение ProductCantDeleteException
 */
public class ProductCantUpdateException extends RuntimeException {
    /**
     * Конструктор
     * @param message - сообщение об ошибке
     */
    public ProductCantUpdateException(String message){
        super(message);
    }
}
