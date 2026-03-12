package drinkshop.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private static int counter = 0;
    private Long id;
    private List<OrderItem> items;
    private double totalPrice;

    public Order(Long id) {
        this.id = id;
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public Order(Long id, List<OrderItem> items, double totalPrice) {
        this.id = id;
        this.items = new ArrayList<>(items);
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", items=" + items +
                ", totalPrice=" + totalPrice +
                '}';
    }


    public void computeTotalPrice() {
        this.totalPrice=items.stream().mapToDouble(OrderItem::getTotal).sum();
    }

    public static void setCounter(int startValue) {
        counter = startValue;
    }
    public static int getCounter() {
        return counter;
    }
}