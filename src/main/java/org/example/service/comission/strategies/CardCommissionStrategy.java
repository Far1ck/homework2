package org.example.service.comission.strategies;

import org.example.service.comission.CommissionStrategy;

public class CardCommissionStrategy implements CommissionStrategy {

    final static String PAYMENT_TYPE = "CARD";

    @Override
    public String getPaymentType() {
        return PAYMENT_TYPE;
    }

    @Override
    public double getCommission(double amount) {
        return amount * 0.02;
    }
}
