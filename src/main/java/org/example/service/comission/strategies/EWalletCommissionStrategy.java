package org.example.service.comission.strategies;

import org.example.service.comission.CommissionStrategy;

public class EWalletCommissionStrategy implements CommissionStrategy {

    final static String PAYMENT_TYPE = "E_WALLET";

    @Override
    public String getPaymentType() {
        return PAYMENT_TYPE;
    }

    @Override
    public double getCommission(double amount) {
        return amount * 0.04;
    }
}
