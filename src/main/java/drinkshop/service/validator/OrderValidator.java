package drinkshop.service.validator;

import drinkshop.domain.Order;
import drinkshop.domain.OrderItem;

public class OrderValidator implements Validator<Order> {

    private final OrderItemValidator itemValidator = new OrderItemValidator();

    @Override
    public void validate(Order order) {

        String errors = "";

        if (order.getId() <= 0)
            errors += "ID comanda invalid!\n";

        if (order.getItems() == null || order.getItems().isEmpty())
            errors += "Comanda fara produse!\n";

        for (OrderItem item : order.getItems()) {
            try {
                itemValidator.validate(item);
            } catch (ValidationException e) {
                errors += e.getMessage();
            }
        }

        if (order.getTotalPrice() < 0)
            errors += "Total invalid!\n";

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }

    public void validate_V2(Order order) {
        String errors = "";

        // 1. Verificare ID Comanda (Punct de decizie 1)
        if (order.getId() <= 0) {
            errors = errors + "ID comanda invalid!\n";
        }

        // 2. Verificare lista de produse null (Punct de decizie 2)
        if (order.getItems() == null) {
            errors = errors + "Lista de produse este null!\n";
        }
        // 3. Verificare lista de produse goala (Punct de decizie 3)
        else if (order.getItems().isEmpty()) {
            errors = errors + "Comanda fara produse!\n";
        }
        else {
            // 4. Parcurgere produse din comanda (Punct de decizie 4 - Bucla)
            for (int i = 0; i < order.getItems().size(); i++) {
                OrderItem item = order.getItems().get(i);

                // 5. Verificare ID Produs (Punct de decizie 5)
                if (item.getProduct().getId() <= 0) {
                    errors = errors + "Produsul de la pozitia " + i + " are ID invalid!\n";
                }

                // 6. Verificare Cantitate Produs (Punct de decizie 6)
                if (item.getQuantity() <= 0) {
                    errors = errors + "Produsul " + item.getProduct().getNume() + " are cantitate invalida!\n";
                }
            }
        }

        // 7. Verificare Pret Total (Punct de decizie 7)
        if (order.getTotalPrice() < 0) {
            errors = errors + "Totalul comenzii este invalid!\n";
        }

        // 8. Verificare finala pentru exceptie (Punct de decizie 8)
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
