package drinkshop.service;

import drinkshop.domain.TipBautura;
import drinkshop.repository.Repository;
import drinkshop.service.validator.TipBauturaValidator;
import drinkshop.service.validator.Validator;

import java.util.List;

public class TipBauturaService {
    private final Repository<Integer, TipBautura> repo;
    private final Validator<TipBautura> validator;

    public TipBauturaService(Repository<Integer, TipBautura> repo) {
        this.repo = repo;

        validator = new TipBauturaValidator();
    }

    public void save(TipBautura tip){
        if(repo.findOne(tip.getId())==null){
            try{
                validator.validate(tip);
                repo.save(tip);
            }catch(Exception e){
                throw new ServiceException(e.getMessage());
            }
        }else{
            throw new ServiceException("Tip cu id-ul "+tip.getId()+" exista deja");
        }
    }
    public List<TipBautura> getAll(){
        return repo.findAll();
    }
}
