package com.kartishan.crud.exceptions;

import lombok.Data;

import java.util.Date;

/**
 * Класс для формирования ответа об ошибке
 * statusCode - код ошибки
 * message - сообщение об ошибке
 * timestamp - время возникновения ошибки
 */
@Data
public class ErrorResponse {
    private Integer statusCode;
    private String message;
    private Date timestamp;
}
