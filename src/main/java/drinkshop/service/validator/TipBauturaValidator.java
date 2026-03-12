package drinkshop.service.validator;

import drinkshop.domain.TipBautura;

public class TipBauturaValidator implements Validator<TipBautura>{
    @Override
    public void validate(TipBautura entity) {
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
