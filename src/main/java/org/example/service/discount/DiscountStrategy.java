package org.example.service.discount;

public interface DiscountStrategy {
    String getCustomerType();
    double getDiscount(double amount);
}
