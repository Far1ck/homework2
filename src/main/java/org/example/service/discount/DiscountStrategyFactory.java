package org.example.service.discount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountStrategyFactory {
    private final Map<String, DiscountStrategy> strategyMap = new HashMap<>();

    public DiscountStrategyFactory(List<DiscountStrategy> strategies) {
        for (DiscountStrategy strategy : strategies) {
            strategyMap.put(strategy.getCustomerType(), strategy);
        }
    }

    public DiscountStrategy getDiscountStrategy(String customerType) {
        DiscountStrategy result = strategyMap.get(customerType);
        if (result == null) {
            throw new IllegalArgumentException("Unknown customer type: " + customerType);
        }
        return result;
    }
}
