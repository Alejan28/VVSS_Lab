package drinkshop.service;

import drinkshop.domain.CategorieBautura;
import drinkshop.domain.Product;
import drinkshop.domain.TipBautura;
import drinkshop.repository.Repository;
import drinkshop.repository.file.FileProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductServiceIntegrationETest {

    private ProductService productService;
    private Repository<Integer, Product> realRepository;

    @Mock
    private Repository<Integer, CategorieBautura> mockCategoryRepo;
    @Mock
    private Repository<Integer, TipBautura> mockTipRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        realRepository = new FileProductRepository("tests_productsE.txt", mockTipRepo, mockCategoryRepo);
        productService = new ProductService(realRepository);
        realRepository.findAll().forEach(entity -> realRepository.delete(entity.getId()));

    }

    @Test
    void testAddProduct_FullIntegration_Success() {
        CategorieBautura cat = new CategorieBautura(1, "BASIC");
        TipBautura tip = new TipBautura(7, "JUICE");
        Product realProduct = new Product(1, "Cola", 5.0, cat, tip);

        when(mockCategoryRepo.findOne(1)).thenReturn(cat);
        when(mockTipRepo.findOne(7)).thenReturn(tip);

        productService.addProduct(realProduct);

        Product savedProduct = realRepository.findOne(1);
        assertNotNull(savedProduct);
        assertEquals("Cola", savedProduct.getNume());
        assertEquals(5.0, savedProduct.getPret());
    }

    @Test
    void testAddProduct_FullIntegration_Duplicate() {
        CategorieBautura cat = new CategorieBautura(1, "BASIC");
        TipBautura tip = new TipBautura(7, "JUICE");

        Product p1 = new Product(1, "Cola", 5.0, cat, tip);
        when(mockCategoryRepo.findOne(1)).thenReturn(cat);
        when(mockTipRepo.findOne(7)).thenReturn(tip);
        productService.addProduct(p1);

        Product p2 = new Product(1, "Pepsi", 6.0, cat, tip);

        assertThrows(ServiceException.class, () -> productService.addProduct(p2));
    }
}
