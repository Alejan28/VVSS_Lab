package drinkshop.service;

import drinkshop.domain.*;
import drinkshop.repository.Repository;
import drinkshop.service.validator.ProductValidator;
import drinkshop.service.validator.Validator;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private final Repository<Integer, Product> productRepo;
    private Validator<Product> validator;


    public ProductService(Repository<Integer, Product> productRepo) {
        this.productRepo = productRepo;

        validator= new ProductValidator();
    }

    public void addProduct(Product p) {
        if(productRepo.findOne(p.getId())==null){
        try{
            validator.validate(p);
            productRepo.save(p);
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }}else{
            throw new ServiceException("Produsul cu id-ul "+p.getId()+" exista deja");
        }
    }

    public void updateProduct(int id, String name, double price, CategorieBautura categorie, TipBautura tip) {
        Product updated = new Product(id, name, price, categorie, tip);
        productRepo.update(updated);
    }

    public void deleteProduct(int id) {
        productRepo.delete(id);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product findById(int id) {
        return productRepo.findOne(id);
    }

    public List<Product> filterByCategorie(CategorieBautura categorie) {
        if (categorie.getNume().equals("ALL")) return getAllProducts();
        return getAllProducts().stream()
                .filter(p -> p.getCategorie().getNume().equals(categorie.getNume()))
                .collect(Collectors.toList());
    }

    public List<Product> filterByTip(TipBautura tip) {
        if (tip.getNume().equals("ALL")) return getAllProducts();
        return getAllProducts().stream()
                .filter(p -> p.getTip().getNume().equals(tip.getNume()))
                .collect(Collectors.toList());
    }
}