package com.kartishan.crud.service;

import com.kartishan.crud.exceptions.*;
import com.kartishan.crud.model.Product;
import com.kartishan.crud.repository.ProductRepository;
import com.kartishan.crud.request.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для управления продуктами.
 */

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Метод для поулчения продукта по его id.
     *
     * @param id Индентификатор продукта.
     * @return Продукт по его id.
     * @throws ProductNotFoundException Если продукт не найден.
     */
    public Product getProductById(UUID id){
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Продукт с id: "+id+ " не найден"));
    }

    /**
     * Метод для получения списка всех продуктов.
     *
     * @return Списов всех продуктов.
     * @throws ProductsCantGetsException Если не получилось получить все продукты.
     */
    public List<Product> getAllProducts(){
        try{
            return productRepository.findAll();
        }catch (Exception ex){
            throw new ProductsCantGetsException("Не удалось получить продукты.");
        }
    }

    /**
     * Метод для Удаления продукта по его id.
     *
     * @param id Индентификатор продукта.
     * @throws ProductNotFoundException Если продукт не найден.
     * @throws ProductCantDeleteException Если не удалось удалить продукт.
     */
    public void deleteProduct(UUID id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с id: "+id+ " не найден"));
        try {
            productRepository.delete(product);
        }
        catch (Exception ex){
            throw new ProductCantDeleteException("Ошибка при удалении продукта с id: " + id);
        }
    }


    /**
     * Метод для добавления продукта.
     *
     * @param productRequest Request для создлания нового продукта.
     * @throws ProductDuplicateException Ошибка если продукт с данным Артикулом уже создан
     * @throws ProductCantCreateException Ошибка если не удалось добавить продукт
     */
    public void addProduct(ProductRequest productRequest){
        if (productRepository.findByArticular(productRequest.getArticular()).isPresent()) {
            throw new ProductDuplicateException("Продукт с таким артикулом уже существует.");
        }
        try {
            Product product = new Product();
            product.setName(productRequest.getName());
            product.setArticular(productRequest.getArticular());
            product.setDescription(productRequest.getDescription());
            product.setCategory(productRequest.getCategory());
            product.setCost(productRequest.getCost());
            product.setAmount(productRequest.getAmount());
            productRepository.save(product);
        }catch (Exception ex) {
            throw new ProductCantCreateException("Не удалось добавить продукт.");
        }
    }

    /**
     * Метод для обновления продукта.
     *
     * @param id Индентификатор продукта.
     * @param product Обновленный продукт продукт.
     * @throws ProductNotFoundException Если продукт не найден.
     * @throws ProductCantCreateException Если не удалось обновить продукт.
     * @throws ProductDuplicateException Если продукт с таким артикулом уже существует.
     */
    public void updateProduct(UUID id, Product product){
        try {
            Product oldProduct = productRepository.findById(id).orElseThrow(
                    () -> new ProductNotFoundException("Продукт с id: "+id+ " не найден"));
            Product findArticular = productRepository.findByArticular(product.getArticular()).orElse(null);
            if (findArticular != null && !findArticular.getId().equals(id)) {
                throw new ProductDuplicateException("Продукт с таким артикулом уже существует.");
            }
            oldProduct.setName(product.getName());
            oldProduct.setArticular(product.getArticular());
            oldProduct.setDescription(product.getDescription());
            oldProduct.setCategory(product.getCategory());
            oldProduct.setCost(product.getCost());
            oldProduct.setAmount(product.getAmount());

            productRepository.save(oldProduct);
        }catch (ProductNotFoundException ex) {
            throw new ProductNotFoundException(ex.getMessage());
        }catch (ProductDuplicateException ex){
            throw new ProductDuplicateException(ex.getMessage());
        }catch (Exception ex) {
            throw new ProductCantCreateException("Ошибка при обновлении продукта с id: " + id);
        }
    }

}
