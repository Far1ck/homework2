package org.example.service.discount.strategies;

import org.example.service.discount.DiscountStrategy;

public class RegularDiscountStrategy implements DiscountStrategy {

    final static String CUSTOMER_TYPE = "REGULAR";

    @Override
    public String getCustomerType() {
        return CUSTOMER_TYPE;
    }

    @Override
    public double getDiscount(double amount) {
        return 0.0;
    }
}
