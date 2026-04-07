package drinkshop.service.validator;

import drinkshop.domain.IngredientReteta;
import drinkshop.domain.Reteta;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RetetaValidator implements Validator<Reteta> {

    @Override
    public void validate(Reteta reteta) {

        String errors = "";

        // 1. Verificare ID (Punct de decizie 1)
        if (reteta.getId() <= 0) {
            errors = errors + "Product ID invalid!\n";
        }

        List<IngredientReteta> ingrediente = reteta.getIngrediente();

        // 2. Verificare dacă lista este null (Punct de decizie 2)
        if (ingrediente == null) {
            errors = errors + "Lista de ingrediente este null!\n";
        }
        // 3. Verificare dacă lista este goală (Punct de decizie 3)
        else if (ingrediente.isEmpty()) {
            errors = errors + "Lista de ingrediente este goala!\n";
        }
        else {
            // 4. Parcurgere ingrediente (Punct de decizie 4 - bucla)
            for (int i = 0; i < ingrediente.size(); i++) {
                IngredientReteta ing = ingrediente.get(i);

                // 5. Verificare cantitate (Punct de decizie 5)
                if (ing.getCantitate() <= 0) {
                    errors = errors + "[" + ing.getDenumire() + "] cantitate invalida!\n";
                }
            }
        }

        // 6. Verificare finală (Punct de decizie 6)
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
