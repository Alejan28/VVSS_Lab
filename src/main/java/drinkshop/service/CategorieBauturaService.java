package drinkshop.service;

import drinkshop.domain.CategorieBautura;
import drinkshop.repository.Repository;
import drinkshop.service.validator.CategorieBauturaValidator;
import drinkshop.service.validator.Validator;

import java.util.List;

public class CategorieBauturaService {
    private final Repository<Integer, CategorieBautura> repository;
    private final  Validator<CategorieBautura> validator;
    public CategorieBauturaService(Repository<Integer, CategorieBautura> repository) {
        this.repository = repository;
        this.validator = new CategorieBauturaValidator();
    }

    public void save(CategorieBautura categorie){
        if(repository.findOne(categorie.getId())==null){
            try{
                validator.validate(categorie);
                repository.save(categorie);
            }catch(Exception e){
                throw new ServiceException(e.getMessage());
            }
        }else{
            throw new ServiceException("Categorie cu id-ul "+categorie.getId()+" exista deja");
        }
    }

    public List<CategorieBautura> getAll(){
        return repository.findAll();
    }

}
