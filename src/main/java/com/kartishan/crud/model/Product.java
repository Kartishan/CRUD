package com.kartishan.crud.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

/**
 * Модель продукта
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private String name;
    @NonNull
    @Column(unique = true)
    private String articular;
    @NonNull
    @Column(length = 1000)
    private String description;
    @NonNull
    private String category;
    @NonNull
    private Double cost;
    @NonNull
    private Integer amount;

    @CreationTimestamp
    @NonNull
    private Date created;

    @CreationTimestamp
    private Date updatedOn;

    public void setAmount(Integer amount) {
        if (amount != null && !amount.equals(this.amount))
            this.updatedOn = new Date();
        this.amount = amount;
    }
}
