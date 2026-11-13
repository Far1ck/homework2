package org.example.service.comission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommissionStrategyFactory {

    private final Map<String, CommissionStrategy> strategyMap = new HashMap<>();

    public CommissionStrategyFactory(List<CommissionStrategy> strategies) {
        for (CommissionStrategy strategy : strategies) {
            strategyMap.put(strategy.getPaymentType(), strategy);
        }
    }

    public CommissionStrategy getCommissionStrategy(String paymentType) {
        CommissionStrategy result = strategyMap.get(paymentType);
        if (result == null) {
            throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        }
        return result;
    }
}
