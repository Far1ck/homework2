package org.example;

import org.example.model.Order;
import org.example.repository.InMemoryOrderRepository;
import org.example.service.OrderService;
import org.example.service.OrderValidator;
import org.example.service.comission.CommissionStrategyFactory;
import org.example.service.comission.strategies.CardCommissionStrategy;
import org.example.service.comission.strategies.CashCommissionStrategy;
import org.example.service.comission.strategies.EWalletCommissionStrategy;
import org.example.service.comission.strategies.TransferCommissionStrategy;
import org.example.service.discount.DiscountStrategyFactory;
import org.example.service.discount.strategies.PremiumDiscountStrategy;
import org.example.service.discount.strategies.RegularDiscountStrategy;
import org.example.service.discount.strategies.VipDiscountStrategy;
import org.example.service.receipt.DefaultReceiptPrinter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DiscountStrategyFactory discountStrategyFactory = new DiscountStrategyFactory(List.of(
                new RegularDiscountStrategy(),
                new VipDiscountStrategy(),
                new PremiumDiscountStrategy()
        ));

        CommissionStrategyFactory commissionStrategyFactory = new CommissionStrategyFactory(List.of(
                new CardCommissionStrategy(),
                new CashCommissionStrategy(),
                new TransferCommissionStrategy(),
                new EWalletCommissionStrategy()
        ));

        OrderService service = new OrderService(
                new InMemoryOrderRepository(),
                new OrderValidator(),
                discountStrategyFactory,
                commissionStrategyFactory,
                new DefaultReceiptPrinter()
        );

        Order order1 = new Order("1", "VIP", "CARD", List.of("Laptop", "Mouse"), 1000.0);
        Order order2 = new Order("2", "REGULAR", "CASH", List.of("Book"), 20.0);

        System.out.println("Final amount 1: " + service.processOrder(order1));
        System.out.println("Final amount 2: " + service.processOrder(order2));
    }
}