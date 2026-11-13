package org.example.service.comission.strategies;

import org.example.service.comission.CommissionStrategy;

public class CashCommissionStrategy implements CommissionStrategy {

    final static String PAYMENT_TYPE = "CASH";

    @Override
    public String getPaymentType() {
        return PAYMENT_TYPE;
    }

    @Override
    public double getCommission(double amount) {
        return 0.0;
    }
}
