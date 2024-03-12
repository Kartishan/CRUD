package com.kartishan.crud.request;

import lombok.Data;

/**
 * Запрос на создание продукта
 */
@Data
public class ProductRequest {
    private String name;
    private String articular;
    private String description;
    private String category;
    private Double cost;
    private Integer amount;
}
