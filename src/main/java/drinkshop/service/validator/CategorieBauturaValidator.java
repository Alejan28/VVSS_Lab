package drinkshop.service.validator;

import drinkshop.domain.CategorieBautura;

public class CategorieBauturaValidator implements Validator<CategorieBautura>{
    @Override
    public void validate(CategorieBautura entity) {
        String errors = "";
        if(entity.getId()<=0){
            errors+="Id invalid!\n";
        }
        if(entity.getNume().isEmpty()){
            errors+="Nume invalid!\n";
        }
        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
