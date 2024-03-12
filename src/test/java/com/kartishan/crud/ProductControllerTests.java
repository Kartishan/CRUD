package com.kartishan.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kartishan.crud.controller.ProductController;
import com.kartishan.crud.exceptions.*;
import com.kartishan.crud.model.Product;
import com.kartishan.crud.request.ProductRequest;
import com.kartishan.crud.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product testProduct;

    @BeforeEach
    public void setup() {
        UUID productId = UUID.randomUUID();
        testProduct = Product.builder()
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
    }


    @Test
    public void TestGetProductById_ReturnProduct_Success() throws Exception {
        UUID productId = testProduct.getId();

        when(productService.getProductById(productId)).thenReturn(testProduct);

        mockMvc.perform(get("/api/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(productService).getProductById(productId);
    }

    @Test
    public void TestGetProductById_ReturnProduct_ProductNotFound() throws Exception {
        UUID productId = UUID.randomUUID();

        when(productService.getProductById(productId)).thenThrow(new ProductNotFoundException("Продукт с id: "+productId+ " не найден"));

       mockMvc.perform(get("/api/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(productService).getProductById(productId);
    }


    @Test
    public void testGetAllProducts_ReturnProducts_Success() throws Exception {
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
        List<Product> testProducts = Arrays.asList(testProduct1, testProduct2);

        when(productService.getAllProducts()).thenReturn(testProducts);

        mockMvc.perform(get("/api/product/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(testProduct1.getId().toString())))
                .andExpect(jsonPath("$[0].name", is(testProduct1.getName())))
                .andExpect(jsonPath("$[1].id", is(testProduct2.getId().toString())))
                .andExpect(jsonPath("$[1].name", is(testProduct2.getName())));

        verify(productService).getAllProducts();
    }

    @Test
    public void testGetAllProducts_ReturnProducts_CantGetProductsException() throws Exception {
        when(productService.getAllProducts()).thenThrow(new ProductsCantGetsException("Тест на ошибку возвращения всех продуктов."));

        mockMvc.perform(get("/api/product/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(productService).getAllProducts();
    }

    @Test
    public void testGetAllProducts_ReturnsEmptyList_Success() throws Exception {
        List<Product> emptyList = Collections.emptyList();

        when(productService.getAllProducts()).thenReturn(emptyList);

        mockMvc.perform(get("/api/product/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(productService).getAllProducts();
    }

    @Test
    public void testDeleteProduct_ReturnMessage_Success() throws Exception {
        UUID productId = testProduct.getId();

        mockMvc.perform(delete("/api/product/delete/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Продукт удален"));

        verify(productService).deleteProduct(productId);
    }

    @Test
    public void testDeleteProduct_ReturnMessage_ProductNotFound() throws Exception {
        UUID productId = UUID.randomUUID();

        doThrow(new ProductNotFoundException("Продукт с id: "+productId+ " не найден")).when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/product/delete/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(productService).deleteProduct(productId);
    }

    @Test
    public void testDeleteProduct_ReturnMessage_ProductCantDelete() throws Exception {
        UUID productId = UUID.randomUUID();

        doThrow(new ProductCantDeleteException("Ошибка при удалении продукта с id: " + productId)).when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/product/delete/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(productService).deleteProduct(productId);
    }


    @Test
    public void testAddProduct_ReturnMessage_Success() throws Exception {
        ProductRequest productRequest = new ProductRequest();

        productRequest.setName("testName");
        productRequest.setArticular("testArticular");
        productRequest.setDescription("testDescription");
        productRequest.setCategory("testCategory");
        productRequest.setCost(202.0);
        productRequest.setAmount(20);

        mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest).getBytes(UTF_8)))
                .andExpect(status().isOk())
                .andExpect(content().string("Продукт добавлен"));

        verify(productService).addProduct(any());
    }
    @Test
    public void testAddProduct_ReturnProductDuplicateException_ProductWithArticularExists() throws Exception {
        ProductRequest productRequest = new ProductRequest();

        productRequest.setName("testName");
        productRequest.setArticular("testArticular");
        productRequest.setDescription("testDescription");
        productRequest.setCategory("testCategory");
        productRequest.setCost(202.0);
        productRequest.setAmount(20);

        doThrow(new ProductDuplicateException("Продукт уже существует.")).when(productService).addProduct(productRequest);

        mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest).getBytes(UTF_8)))
                .andExpect(status().isConflict());

        verify(productService).addProduct(any());
    }

    @Test
    public void testAddProduct_ReturnProductCantCreateException_ErrorOnSave() throws Exception{
        ProductRequest productRequest = new ProductRequest();

        productRequest.setName("testName");
        productRequest.setArticular("testArticular");
        productRequest.setDescription("testDescription");
        productRequest.setCategory("testCategory");
        productRequest.setCost(202.0);
        productRequest.setAmount(20);

        doThrow(new ProductCantDeleteException("Ошибка при добавлении продукта.")).when(productService).addProduct(productRequest);

        mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest).getBytes(UTF_8)))
                .andExpect(status().isBadRequest());

        verify(productService).addProduct(any());
    }

    @Test
    public void updateProduct_ReturnMessage_Success() throws Exception{
        UUID productId = testProduct.getId();

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

        doNothing().when(productService).updateProduct(productId, testProduct);

        mockMvc.perform(put("/api/product/update/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct).getBytes(UTF_8)))
                .andExpect(status().isOk())
                .andExpect(content().string("Продукт обновлен"));

        verify(productService).updateProduct(productId, testProduct);
    }

    @Test
    public void updateProduct_ReturnProductNotFoundException_ProductNotFound() throws Exception {
        UUID invalidId = UUID.randomUUID();

        Product product = Product.builder()
                .name("testName")
                .articular("testArticular")
                .description("testDescription")
                .category("testCategory")
                .cost(202.0)
                .amount(20)
                .created(new Date())
                .build();

        doThrow(new ProductNotFoundException("Ошибка при обновлении продукта с id: " + invalidId)).when(productService).updateProduct(invalidId, product);

        mockMvc.perform(put("/api/product/update/" + invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound());

        verify(productService).updateProduct(invalidId, product);
    }

    @Test
    public void updateProduct_ReturnProductCantCreateException_ErrorUpdate() throws Exception {
        UUID productId = testProduct.getId();

        Product product = Product.builder()
                .name("testName")
                .articular("testArticular")
                .description("testDescription")
                .category("testCategory")
                .cost(202.0)
                .amount(20)
                .created(new Date())
                .build();

        doThrow(new ProductCantDeleteException("Ошибка при обновлении продукта с id: " + productId)).when(productService).updateProduct(productId, product);

        mockMvc.perform(put("/api/product/update/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest());

        verify(productService).updateProduct(productId, product);
    }

    @Test
    public void updateProduct_ReturnProductDuplicateException_DuplicateArticular() throws Exception {
        UUID productId = testProduct.getId();
        String articular = testProduct.getArticular();
        Product testProduct = Product.builder()
                .id(productId)
                .name("testName")
                .articular(articular)
                .description("testDescription")
                .category("testCategory")
                .cost(202.0)
                .amount(20)
                .created(new Date())
                .updatedOn(new Date())
                .build();

        doThrow(new ProductDuplicateException("Продукт с таким артикулом уже существует.")).when(productService).updateProduct(productId, testProduct);

        mockMvc.perform(put("/api/product/update/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isConflict());

        verify(productService).updateProduct(productId, testProduct);
    }
}

