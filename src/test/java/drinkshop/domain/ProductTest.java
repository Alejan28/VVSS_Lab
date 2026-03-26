package drinkshop.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class ProductTest {

    Product product;
    CategorieBautura categorie;
    TipBautura tip;


    @BeforeEach
    void setUp() {
        categorie= new CategorieBautura(100, "JUICE");
        tip = new TipBautura(100, "WATER_BASED");
        product =new Product(100, "Limonada", 10.0,categorie,tip);
    }

    @AfterEach
    void tearDown() {
        product = null;
    }

    @Test
    void getId() {
        assert 100 == product.getId();
    }

    @Test
    void getNume() {
        assert "Limonada".equals(product.getNume());
    }

    @Test
    void getPret() {
        assert 10.0 == product.getPret();
    }

    @Test
    void getCategorie() {
        assert categorie.getNume().equals(product.getCategorie().getNume());
        assert categorie.getId().equals(product.getCategorie().getId());
    }

    @Test
    void setCategorie() {
        categorie.setNume("SMOOTHIE");
        product.setCategorie(categorie);
        assert categorie.getNume().equals(product.getCategorie().getNume());
        assert categorie.getId().equals(product.getCategorie().getId());
    }

    @Test
    void getTip() {
        assert tip.getNume().equals(product.getTip().getNume());
        assert tip.getId().equals(product.getTip().getId());
    }

    @Test
    void setTip() {
        tip.setNume("BASIC");
        product.setTip(tip);
        assert tip.getNume().equals(product.getTip().getNume());
        assert tip.getId().equals(product.getTip().getId());
    }

    @Test
    void setNume() {
        product.setNume("newLimonada");
        assert "newLimonada".equals(product.getNume());
    }

    @Test
    void setPret() {
        product.setPret(10.05);
        assert 10.05 == product.getPret();
    }

    @Test
    void testToString() {
        System.out.println(product.toString());
        assert "Limonada (JUICE, WATER_BASED) - 10.0 lei".equals(product.toString());
    }
}