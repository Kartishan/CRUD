package com.kartishan.crud;


import com.kartishan.crud.exceptions.*;
import com.kartishan.crud.model.Product;
import com.kartishan.crud.repository.ProductRepository;
import com.kartishan.crud.request.ProductRequest;
import com.kartishan.crud.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @Test
    void getProductById_ReturnProduct_Success() {
        UUID productId = UUID.randomUUID();
        Product testProduct = Product.builder()
                .id(productId)
                .name("testName")
                .articular("testArticular")
                .description("testDescription")
                .category("testCategory")
                .cost(202.0)
                .amount(20)
                .created(new Date())
                .updatedOn(new Date())
                .build();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        Product result = productService.getProductById(productId);

        Assertions.assertEquals(testProduct, result);
        verify(productRepository).findById(productId);
    }
    @Test
    void getProductById_ReturnProductNotFoundException_ProductNotFound() {
        UUID invalidProductId = UUID.randomUUID();

        Mockito.when(productRepository.findById(invalidProductId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(invalidProductId));
        verify(productRepository).findById(invalidProductId);
    }


    @Test
    void getAllProducts_ReturnProducts_Success() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        Product testProduct1 = Product.builder()
                .id(productId1)
                .name("testName1")
                .articular("testArticular1")
                .description("testDescription1")
                .category("testCategory1")
                .cost(202.0)
                .amount(20)
                .created(new Date())
                .updatedOn(new Date())
                .build();
        Product testProduct2 = Product.builder()
                .id(productId2)
                .name("testName2")
                .articular("testArticular2")
                .description("testDescription2")
                .category("testCategory1")
                .cost(203.0)
                .amount(21)
                .created(new Date())
                .updatedOn(new Date())
                .build();
        List<Product> testProducts = Arrays.asList(
                testProduct1,
                testProduct2
        );

        Mockito.when(productRepository.findAll()).thenReturn(testProducts);

        List<Product> result = productService.getAllProducts();

        Assertions.assertEquals(testProducts, result);

        verify(productRepository).findAll();
    }
    @Test
    void getAllProducts_ReturnProductsCantGetsException_ProductsCantGets() {
        Mockito.when(productRepository.findAll()).thenThrow(new RuntimeException("Тест на ошибку возвращения всех продуктов."));

        Assertions.assertThrows(ProductsCantGetsException.class, () -> {
            productService.getAllProducts();
        });

        verify(productRepository).findAll();
    }
    @Test
    void getAllProducts_ReturnsEmptyList_Success() {
        List<Product> emptyList = Collections.emptyList();

        Mockito.when(productRepository.findAll()).thenReturn(emptyList);

        List<Product> result = productService.getAllProducts();

        Assertions.assertEquals(emptyList, result);

        verify(productRepository).findAll();
    }


    @Test
    void deleteProduct_ReturnVoid_Success() {
        UUID productId = UUID.randomUUID();
        Product testProduct = Product.builder()
                .id(productId)
                .name("testName")
                .articular("testArticular")
                .description("testDescription")
                .category("testCategory")
                .cost(202.0)
                .amount(20)
                .created(new Date())
                .updatedOn(new Date())
                .build();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        productService.deleteProduct(productId);

        verify(productRepository).findById(productId);

        verify(productRepository).delete(testProduct);
    }

    @Test
    void deleteProduct_ReturnProductNotFoundException_ProductNotFound() {
        UUID invalidProductId = UUID.randomUUID();

        Mockito.when(productRepository.findById(invalidProductId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(invalidProductId));

        verify(productRepository).findById(invalidProductId);

        verify(productRepository, never()).delete(Mockito.any());
    }

    @Test
    void deleteProduct_ReturnProductCantDeleteException_ProductCantDelete() {
        UUID productId = UUID.randomUUID();
        Product testProduct = Product.builder()
                .id(productId)
                .name("testName")
                .articular("testArticular")
                .description("testDescription")
                .category("testCategory")
                .cost(202.0)
                .amount(20)
                .created(new Date())
                .updatedOn(new Date())
                .build();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        Mockito.doThrow(new RuntimeException("Тест на ошибку при удалении продукта.")).when(productRepository).delete(testProduct);

        Assertions.assertThrows(ProductCantDeleteException.class, () -> {
            productService.deleteProduct(productId);
        });

        verify(productRepository).findById(productId);

        verify(productRepository).delete(testProduct);
    }


    @Test
    void addProduct_ReturnVoid_Success() {
        ProductRequest productRequest = new ProductRequest();

        productRequest.setName("testName");
        productRequest.setArticular("testArticular");
        productRequest.setDescription("testDescription");
        productRequest.setCategory("testCategory");
        productRequest.setCost(202.0);
        productRequest.setAmount(20);
        when(productRepository.findByArticular(productRequest.getArticular())).thenReturn(java.util.Optional.empty());

        productService.addProduct(productRequest);

        verify(productRepository, times(1)).findByArticular(productRequest.getArticular());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void addProduct_ReturnProductDuplicateException_ProductWithArticularExists() {
        UUID productId = UUID.randomUUID();
        Product testProduct = Product.builder()
                .id(productId)
                .name("testName")
                .articular("testArticular")
                .description("testDescription")
                .category("testCategory")
                .cost(202.0)
                .amount(20)
                .created(new Date())
                .updatedOn(new Date())
                .build();

        ProductRequest productRequest = new ProductRequest();

        productRequest.setName("testName");
        productRequest.setArticular("testArticular");
        productRequest.setDescription("testDescription");
        productRequest.setCategory("testCategory");
        productRequest.setCost(202.0);
        productRequest.setAmount(20);

        Mockito.when(productRepository.findByArticular(productRequest.getArticular())).thenReturn(Optional.of(new Product()));

        Assertions.assertThrows(ProductDuplicateException.class, () -> {
            productService.addProduct(productRequest);
        });

        verify(productRepository).findByArticular(testProduct.getArticular());
        verify(productRepository, never()).save(any());
    }

    @Test
    public void addProduct_ReturnProductCantCreateException_ErrorOnSave(){
        ProductRequest productRequest = new ProductRequest();

        productRequest.setName("testName");
        productRequest.setArticular("testArticular");
        productRequest.setDescription("testDescription");
        productRequest.setCategory("testCategory");
        productRequest.setCost(202.0);
        productRequest.setAmount(20);

        Mockito.when(productRepository.findByArticular(productRequest.getArticular())).thenReturn(Optional.empty());
        Mockito.doThrow(new RuntimeException("Тест на ошибку при добавлении продукта.")).when(productRepository).save(Mockito.any());

        Assertions.assertThrows(ProductCantCreateException.class, () -> {
            productService.addProduct(productRequest);
        });

        verify(productRepository).findByArticular(productRequest.getArticular());
        verify(productRepository).save(Mockito.any());
    }


    @Test
    public void updateProduct_ReturnVoid_Success(){
        UUID productId = UUID.randomUUID();
        Product testProduct = Product.builder()
                .id(productId)
                .name("testName")
                .articular("testArticular")
                .description("testDescription")
                .category("testCategory")
                .cost(202.0)
                .amount(20)
                .created(new Date())
                .updatedOn(new Date())
                .build();

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setCreated(testProduct.getCreated());
        updatedProduct.setName("Updated Product");
        updatedProduct.setArticular("updatedArticular");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setCategory("Updated Category");
        updatedProduct.setCost(150.0);
        updatedProduct.setAmount(15);

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productRepository.findByArticular(updatedProduct.getArticular())).thenReturn(Optional.empty());

        productService.updateProduct(productId, updatedProduct);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).findByArticular(updatedProduct.getArticular());
        verify(productRepository, times(1)).save(any(Product.class));
    }
    @Test
    public void updateProduct_ReturnProductNotFoundException_ProductNotFound(){
        UUID invalidProductId = UUID.randomUUID();

        when(productRepository.findById(invalidProductId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(invalidProductId, new Product()));

        verify(productRepository, times(1)).findById(invalidProductId);
        verify(productRepository, never()).save(Mockito.any());
    }
    @Test
    public void updateProduct_ReturnProductCantCreateException_ErrorUpdate(){
        UUID productId = UUID.randomUUID();
        Product testProduct = Product.builder()
                .id(productId)
                .name("testName")
                .articular("testArticular")
                .description("testDescription")
                .category("testCategory")
                .cost(202.0)
                .amount(20)
                .created(new Date())
                .updatedOn(new Date())
                .build();

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setCreated(testProduct.getCreated());
        updatedProduct.setName("Updated Product");
        updatedProduct.setArticular("updatedArticular");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setCategory("Updated Category");
        updatedProduct.setCost(150.0);
        updatedProduct.setAmount(15);

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(updatedProduct)).thenThrow(new RuntimeException("Тест на ошибку при обновлении продукта."));

        assertThrows(ProductCantCreateException.class, () -> productService.updateProduct(productId, updatedProduct));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).findByArticular(updatedProduct.getArticular());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void updateProduct_ReturnProductDuplicateException_ProductDuplicate(){
        UUID productId = UUID.randomUUID();
        Product testProduct = Product.builder()
                .id(UUID.randomUUID())
                .name("existingName")
                .articular("existingArticular")
                .description("existingDescription")
                .category("existingCategory")
                .cost(100.0)
                .amount(10)
                .created(new Date())
                .updatedOn(new Date())
                .build();

        Product updatedProduct = Product.builder()
                .id(productId)
                .name("updatedName")
                .articular(testProduct.getArticular())
                .description("updatedDescription")
                .category("updatedCategory")
                .cost(150.0)
                .amount(15)
                .created(new Date())
                .updatedOn(new Date())
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productRepository.findByArticular(updatedProduct.getArticular())).thenReturn(Optional.of(testProduct));

        assertThrows(ProductDuplicateException.class, () -> productService.updateProduct(productId, updatedProduct));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).findByArticular(updatedProduct.getArticular());
        verify(productRepository, never()).save(any(Product.class));
    }
}
