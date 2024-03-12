package com.kartishan.crud.controller;

import com.kartishan.crud.model.Product;
import com.kartishan.crud.request.ProductRequest;
import com.kartishan.crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для CRUD
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    /**
     * Метод для получения продуктов по id.
     *
     * @param id Индентификатор продукта.
     * @return Возвращает Product.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") UUID id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Метод для получения всех продуктов.
     *
     * @return Список продуктов.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getProductsById(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Метод для удаления продуктов по его id.
     *
     * @param id Индентификатор продукта.
     * @return Сообщение "Продукт удален".
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable UUID id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Продукт удален");
    }

    /**
     * Меотод для добавления продукта.
     *
     * @param productRequest
     * @return Сообщение, что продукт добавлен.
     */
    @PostMapping("/create")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest){
        productService.addProduct(productRequest);
        return ResponseEntity.ok("Продукт добавлен");
    }

    /**
     * Метод для обноаления продукта.
     *
     * @param id
     * @param product
     * @return Сообщение, что пробукт обновлен
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable UUID id, @RequestBody Product product){
        productService.updateProduct(id, product);
        return ResponseEntity.ok("Продукт обновлен");
    }


}
