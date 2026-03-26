package drinkshop.reports;

import drinkshop.domain.Order;
import drinkshop.repository.Repository;

public class DailyReportService {
    private Repository<Long, Order> repo;

    public DailyReportService(Repository<Long, Order> repo) {
        this.repo = repo;
    }

    public double getTotalRevenue() {
        return repo.findAll().stream().mapToDouble(Order::getTotalPrice).sum();
    }

    public int getTotalOrders() {
        return repo.findAll().size();
    }
}