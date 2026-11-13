package org.example.service.comission;

public interface CommissionStrategy {
    String getPaymentType();
    double getCommission(double amount);
}
