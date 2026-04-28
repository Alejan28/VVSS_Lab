package drinkshop.domain;

import drinkshop.repository.Repository;
import drinkshop.repository.file.FileCategorieBauturaRepository;
import drinkshop.repository.file.FileProductRepository;
import drinkshop.repository.file.FileTipBauturaRepository;
import drinkshop.service.ProductService;
import org.junit.jupiter.api.*;

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

    // ---------------- VALID ----------------

    @Test
    @DisplayName("TC_1 - produs valid")
    void addValidProduct() throws Exception {
        assertValid("preparat rece", 13);
    }

    // ---------------- ECP INVALID ----------------

    @Test
    @DisplayName("TC_2 - pret negativ")
    void addInvalidECP_TC2() {
        assertInvalid("preparat rece", -13.0);
    }

    @Test
    @DisplayName("TC_8 - nume gol")
    void addInvalidProduct() {
        assertInvalid("", 13.0);
    }

    @Test
    @DisplayName("TC_9 - nume gol + pret negativ")
    void addInvalidECP_TC9() {
        assertInvalid("", -14.99);
    }

    // ---------------- BVA INVALID ----------------

    @Test
    void addInvalidProductBVA() { assertInvalid("", -0.01); }

    @Test
    void addInvalidProduct_BVA_TC2() { assertInvalid("", 0.0); }

    @Test
    void addInvalidProduct_BVA_TC3() { assertInvalid("", 0.01); }

    @Test
    void addInvalidProduct_BVA_TC7() { assertInvalid("A", -0.01); }

    @Test
    void addInvalidProduct_BVA_TC8() { assertInvalid("A", 0.0); }

    @Test
    void addInvalidProduct_BVA_TC10() { assertInvalid("a".repeat(255), -0.01); }

    @Test
    void addInvalidProduct_BVA_TC11() { assertInvalid("a".repeat(255), 0.0); }

    @Test
    void addInvalidProduct_BVA_TC13() { assertInvalid("a".repeat(254), -0.01); }

    @Test
    void addInvalidProduct_BVA_TC14() { assertInvalid("a".repeat(254), 0.0); }

    @Test
    void addInvalidProduct_BVA_TC16() { assertInvalid("a".repeat(256), -0.01); }

    @Test
    void addInvalidProduct_BVA_TC17() { assertInvalid("a".repeat(256), 0.0); }

    @Test
    void addInvalidProduct_BVA_TC18() { assertInvalid("a".repeat(256), 0.01); }

    // ---------------- BVA VALID ----------------

    @Test
    void addValidProductBVA() throws Exception {
        assertValid("A", 0.01);
    }

    @Test
    void addValidProduct_BVA_TC12() throws Exception {
        assertValid("a".repeat(255), 0.01);
    }

    @Test
    void addValidProduct_BVA_TC15() throws Exception {
        assertValid("a".repeat(254), 0.01);
    }
}