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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductServiceIntegrationRTest {
    private ProductService productService;
    private Repository<Integer, Product> realRepository;

    @Mock
    private Product mockProduct;

    @Mock
    private Repository<Integer, CategorieBautura> mockCategoryRepo;
    @Mock
    private Repository<Integer, TipBautura> mockTipRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        realRepository = new FileProductRepository("tests_products.txt", mockTipRepo, mockCategoryRepo);
        productService = new ProductService(realRepository);
        realRepository.findAll().forEach(entity -> realRepository.delete(entity.getId()));
    }

    @Test
    void testAddProduct_Integration_WithRealRepo() {

        CategorieBautura cat = new CategorieBautura(1, "BASIC");
        TipBautura tip = new TipBautura(7, "JUICE");

        when(mockProduct.getId()).thenReturn(10);
        when(mockProduct.getNume()).thenReturn("Cola");
        when(mockProduct.getPret()).thenReturn(10.0);

        when(mockProduct.getCategorie()).thenReturn(cat);
        when(mockProduct.getTip()).thenReturn(tip);

        when(mockCategoryRepo.findOne(1)).thenReturn(cat);
        when(mockTipRepo.findOne(1)).thenReturn(tip);

        productService.addProduct(mockProduct);

        assertNotNull(realRepository.findOne(10));
        verify(mockProduct, atLeastOnce()).getId();
    }


    private void setupMockProduct(int id, String nume) {
        CategorieBautura cat = new CategorieBautura(1, "BASIC");
        TipBautura tip = new TipBautura(1, "JUICE");

        when(mockProduct.getId()).thenReturn(id);
        when(mockProduct.getNume()).thenReturn(nume);
        when(mockProduct.getPret()).thenReturn(10.0);
        when(mockProduct.getCategorie()).thenReturn(cat);
        when(mockProduct.getTip()).thenReturn(tip);

        when(mockCategoryRepo.findOne(anyInt())).thenReturn(cat);
        when(mockTipRepo.findOne(anyInt())).thenReturn(tip);
    }

    @Test
    void testAddProduct_DuplicateId_Integration() {
        setupMockProduct(1, "Existent");
        realRepository.save(mockProduct);

        assertThrows(ServiceException.class, () -> productService.addProduct(mockProduct));
    }

}
