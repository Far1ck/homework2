package org.example.service.receipt;

import org.example.model.Order;

public interface ReceiptPrinter {
    void print(Order order, double discount, double commission, double finalAmount);
}
