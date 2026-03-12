package drinkshop.service;

import drinkshop.domain.Reteta;
import drinkshop.repository.Repository;
import drinkshop.service.validator.RetetaValidator;
import drinkshop.service.validator.Validator;

import java.util.List;

public class RetetaService {

    private final Repository<Integer, Reteta> retetaRepo;
    private Validator<Reteta> validator;

    public RetetaService(Repository<Integer, Reteta> retetaRepo) {
        this.retetaRepo = retetaRepo;
        validator = new RetetaValidator();
    }

    public void addReteta(Reteta r) {
        if(retetaRepo.findOne(r.getId())==null){
            try{
                validator.validate(r);
                retetaRepo.save(r);
            } catch(Exception e){
                throw new ServiceException(e.getMessage());
            }
        }else{
            throw new ServiceException("Reteta cu id-ul "+r.getId()+" exista deja");
        }
    }

    public void updateReteta(Reteta r) {
        retetaRepo.update(r);
    }

    public void deleteReteta(int id) {
        retetaRepo.delete(id);
    }

    public Reteta findById(int id) {
        return retetaRepo.findOne(id);
    }

    public List<Reteta> getAll() {
        return retetaRepo.findAll();
    }
}