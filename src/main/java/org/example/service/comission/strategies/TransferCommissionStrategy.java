package org.example.service.comission.strategies;

import org.example.service.comission.CommissionStrategy;

public class TransferCommissionStrategy implements CommissionStrategy {

    final static String PAYMENT_TYPE = "TRANSFER";

    @Override
    public String getPaymentType() {
        return PAYMENT_TYPE;
    }

    @Override
    public double getCommission(double amount) {
        return amount * 0.01;
    }
}
