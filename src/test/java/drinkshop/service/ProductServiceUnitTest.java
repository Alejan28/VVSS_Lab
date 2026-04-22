package drinkshop.service;

import drinkshop.domain.Product;
import drinkshop.repository.Repository;
import drinkshop.service.validator.ProductValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @Mock
    private Repository<Integer, Product> mockRepository;
    @Mock
    private Product mockProduct;

    @InjectMocks
    private ProductService productService;

    @Test
    void testAddProduct_Success() {
        when(mockProduct.getId()).thenReturn(1);
        when(mockProduct.getNume()).thenReturn("Cola");
        when(mockProduct.getPret()).thenReturn(10.0);
        when(mockRepository.findOne(1)).thenReturn(null);

        productService.addProduct(mockProduct);

        verify(mockRepository).save(mockProduct);
    }

    @Test
    void testAddProduct_DuplicateId_ThrowsException() {
        when(mockProduct.getId()).thenReturn(1);
        when(mockRepository.findOne(1)).thenReturn(mockProduct);

        assertThrows(ServiceException.class, () -> productService.addProduct(mockProduct));
        verify(mockRepository, never()).save(any());
    }

}
