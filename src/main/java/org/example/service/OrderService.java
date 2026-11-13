package org.example.service;

import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.example.service.comission.CommissionStrategyFactory;
import org.example.service.discount.DiscountStrategyFactory;
import org.example.service.receipt.ReceiptPrinter;

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

/* Пояснения по принципам SOLID:
S:  Класс должен иметь только одну причину для изменения. В данном случае причиной изменения класса OrderService
    может быть только смена алгоритма обработки заказа (например, порядка действий, или добавление новых).
    В старом варианте причинами для изменения помимо этой могли стать изменение структуры чека или способа его печати,
    изменение способа валидации и т.д., что нарушало данный принцип.
O:  Класс должен быть открыт для расширения, но закрыт для изменения. Теперь в случае необходимости к примеру добавить
    новый способ оплаты нам нужно будет просто добавить еще один класс, не меняя ни строчки кода в уже существующих.
    Раньше же нам пришлось бы лезть, к примеру, в конструкцию if-else, тем самым есть риск нарушить логику уже
    существующего кода.
L:  Любая реализация интерфейсов DiscountStrategy или ReceiptPrinter например корректно заменяет интерфейс без поломки
    программы. В старом варианте принцип вообще не применялся, так как не было наследников или интерфейсов.
I:  Все интерфейсы маленькие и узкоспециализированные, и все реализации реализуют все методы в полной мере.
    В старом варианте программы принцип таже был не применялся изза отсутствия интерфейсов.
D:  OrderService зависит от интерфейсов (абстракций), например от ReceiptPrinter и OrderRepository,
    а не от конкретных реализаций вроде вывода в консоль или записи в ArrayList. Изза этого в старом варианте программы
    не было гибкости и масштабируемости, теперь же мы можем спокойно добавить например класс сохранения заказа в БД
 */

