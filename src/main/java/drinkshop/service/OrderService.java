package drinkshop.service;

import drinkshop.domain.Order;
import drinkshop.domain.OrderItem;
import drinkshop.domain.Product;
import drinkshop.repository.Repository;
import drinkshop.service.validator.OrderValidator;
import drinkshop.service.validator.Validator;

import java.util.List;

public class OrderService {

    private final Repository<Long, Order> orderRepo;
    private final Repository<Integer, Product> productRepo;
    private final Validator<Order> validator;


    public OrderService(Repository<Long, Order> orderRepo, Repository<Integer, Product> productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;

        validator = new OrderValidator();
    }

    public void addOrder(Order o) {
        if(orderRepo.findOne(o.getId())==null){
            try{
                validator.validate(o);
                orderRepo.save(o);
            }catch (Exception e){
                throw new ServiceException(e.getMessage());
            }

        }else{
            throw new ServiceException("Comanda cu id-ul "+o.getId()+" exista deja");
        }
    }

    public void updateOrder(Order o) {
        orderRepo.update(o);
    }

    public void deleteOrder(Long id) {
        orderRepo.delete(id);
    }

    public List<Order> getAllOrders() {
//        return StreamSupport.stream(orderRepo.findAll().spliterator(), false)
//                .collect(Collectors.toList());
        return orderRepo.findAll();
    }

    public Order findById(Long id) {
        return orderRepo.findOne(id);
    }

    public double computeTotal(Order o) {
        return o.getItems().stream()
                .mapToDouble(i -> productRepo.findOne(i.getProduct().getId()).getPret() * i.getQuantity())
                .sum();
    }

    public void addItem(Order o, OrderItem item) {
        o.getItems().add(item);
        orderRepo.update(o);
    }

    public void removeItem(Order o, OrderItem item) {
        o.getItems().remove(item);
        orderRepo.update(o);
    }
}