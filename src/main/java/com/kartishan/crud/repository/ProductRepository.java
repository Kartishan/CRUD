package com.kartishan.crud.repository;

import com.kartishan.crud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


/**
 * Репозиторий продукта
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findById(UUID id);
    Optional<Product> findByArticular(String articular);
}
