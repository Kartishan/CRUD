package com.kartishan.crud.exceptions;

/**
 * Исключение ProductCantDeleteException
 */
public class ProductDuplicateException extends RuntimeException {
    /**
     * Конструктор
     * @param message - сообщение об ошибке
     */
    public ProductDuplicateException(String message){
        super(message);
    }
}
