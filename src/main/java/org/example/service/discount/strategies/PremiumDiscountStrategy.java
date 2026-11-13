package org.example.service.discount.strategies;

import org.example.service.discount.DiscountStrategy;

public class PremiumDiscountStrategy implements DiscountStrategy {

    final static String CUSTOMER_TYPE = "PREMIUM";

    @Override
    public String getCustomerType() {
        return CUSTOMER_TYPE;
    }

    @Override
    public double getDiscount(double amount) {
        return amount * 0.1;
    }
}
