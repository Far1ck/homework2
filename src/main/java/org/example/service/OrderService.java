package org.example.service;

import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.example.service.comission.CommissionStrategyFactory;
import org.example.service.discount.DiscountStrategyFactory;
import org.example.service.receipt.ReceiptPrinter;

import java.util.ArrayList;
import java.util.List;

/**
 Контекст: Тебе достался в наследство код от предыдущего разработчика — сервис обработки заказов интернет-магазина OrderService.
 Он работает, тесты (если бы они были) прошли бы, заказчик доволен... пока не попросил добавить новый тип клиента или способ оплаты.
 Тут выясняется, что каждое такое "простое" изменение требует лезть в один и тот же метод на 80 строк, где всё завязано друг на друга,
 и есть риск что-то сломать в соседней логике.
 Твоя задача: не переписать сервис с нуля, а отрефакторить — сохранив то же поведение (те же скидки, та же комиссия, тот же чек)
 Опирайся на SOLID и паттерны проектирования
Lombok использовать нельзя, все писать руками.
 */
public class OrderService {

    private final OrderRepository repository;
    private final OrderValidator validator;
    private final DiscountStrategyFactory discountStrategyFactory;
    private final CommissionStrategyFactory commissionStrategyFactory;
    private final ReceiptPrinter receiptPrinter;

    public OrderService(OrderRepository repository,
                        OrderValidator validator,
                        DiscountStrategyFactory discountStrategyFactory, CommissionStrategyFactory commissionStrategyFactory, ReceiptPrinter receiptPrinter) {
        this.repository = repository;
        this.validator = validator;
        this.discountStrategyFactory = discountStrategyFactory;
        this.commissionStrategyFactory = commissionStrategyFactory;
        this.receiptPrinter = receiptPrinter;
    }

    public double processOrder(Order order) {

        // --- валидация ---
        validator.validate(order);

        // --- расчёт скидки по типу клиента ---
        double discount = discountStrategyFactory.getDiscountStrategy(order.getCustomerType())
                .getDiscount(order.getAmount());

        // --- расчёт комиссии по способу оплаты ---
        double commission = commissionStrategyFactory.getCommissionStrategy(order.getPaymentType())
                .getCommission(order.getAmount());

        // --- итоговая сумма ---
        double finalAmount = order.getAmount() - discount + commission;

        // --- формирование чека прямо тут ---
        receiptPrinter.print(order, discount, commission, finalAmount);

        // --- "сохранение" заказа  ---
        repository.save(order);

        return finalAmount;
    }
}

