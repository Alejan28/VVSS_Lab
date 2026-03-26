package drinkshop.domain;

import drinkshop.repository.Repository;
import drinkshop.repository.file.FileCategorieBauturaRepository;
import drinkshop.repository.file.FileProductRepository;
import drinkshop.repository.file.FileTipBauturaRepository;
import drinkshop.service.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductAddTest {

    Product product;
    ProductService service;

    @BeforeEach
    void setUp() {
        CategorieBautura categorie = new CategorieBautura(100, "JUICE");
        TipBautura tip = new TipBautura(100, "WATER_BASED");

        product = new Product(102, "default", 10, categorie, tip);

        Repository<Integer, TipBautura> tipRepo =
                new FileTipBauturaRepository("data/TipBautura.txt");
        Repository<Integer, CategorieBautura> categorieRepo =
                new FileCategorieBauturaRepository("data/CategorieBautura.txt");
        Repository<Integer, Product> repo =
                new FileProductRepository("data/products.txt", tipRepo, categorieRepo);

        service = new ProductService(repo);
    }

    @AfterEach
    void tearDown() {
        try {
            service.deleteProduct(product.getId());
        } catch (Exception ignored) {}
    }



    void assertValid(String nume, double pret) throws Exception {
        product.setNume(nume);
        product.setPret(pret);

        service.addProduct(product);

        Product p = service.getProduct(product.getId());
        assertEquals(nume, p.getNume());
        assertEquals(pret, p.getPret());
    }

    void assertInvalid(String nume, double pret) {
        product.setNume(nume);
        product.setPret(pret);

        assertThrows(Exception.class,
                () -> service.addProduct(product));
    }



    @Test
    @DisplayName("TC_1 - produs valid")
    void addValidProduct() throws Exception {
        assertValid("preparat rece", 13);
    }



    static Stream<Arguments> invalidECPCases() {
        return Stream.of(
                Arguments.of("preparat rece", -13.0),   // TC_2
                Arguments.of("", 13.0),                 // TC_8
                Arguments.of("", -14.99)                // TC_9
        );
    }

    @ParameterizedTest(name = "Invalid basic: nume={0}, pret={1}")
    @MethodSource("invalidECPCases")
    void addInvalidECP(String nume, double pret) {
        assertInvalid(nume, pret);
    }


    static Stream<Arguments> bvaInvalidCases() {
        return Stream.of(
                Arguments.of("", -0.01),          // TC1_BVA
                Arguments.of("", 0.0),            // TC2_BVA
                Arguments.of("", 0.01),           // TC3_BVA
                Arguments.of("A", -0.01),         // TC7_BVA
                Arguments.of("A", 0.0),           // TC8_BVA
                Arguments.of("a".repeat(255), -0.01), // TC10_BVA
                Arguments.of("a".repeat(255), 0.0),   // TC11_BVA
                Arguments.of("a".repeat(254), -0.01), // TC13_BVA
                Arguments.of("a".repeat(254), 0.0),   // TC14_BVA
                Arguments.of("a".repeat(256), -0.01), // TC16_BVA
                Arguments.of("a".repeat(256), 0.0),   // TC17_BVA
                Arguments.of("a".repeat(256), 0.01)   // TC18_BVA
        );
    }

    @ParameterizedTest(name = "BVA invalid: nume length={0}, pret={1}")
    @MethodSource("bvaInvalidCases")
    void addInvalidProduct_BVA(String nume, double pret) {
        assertInvalid(nume, pret);
    }


    static Stream<Arguments> bvaValidCases() {
        return Stream.of(
                Arguments.of("A", 0.01),                // TC9_BVA
                Arguments.of("a".repeat(255), 0.01),   // TC12_BVA
                Arguments.of("a".repeat(254), 0.01)    // TC15_BVA
        );
    }

    @ParameterizedTest(name = "BVA valid: nume length={0}, pret={1}")
    @MethodSource("bvaValidCases")
    void addValidProduct_BVA(String nume, double pret) throws Exception {
        assertValid(nume, pret);
    }
}