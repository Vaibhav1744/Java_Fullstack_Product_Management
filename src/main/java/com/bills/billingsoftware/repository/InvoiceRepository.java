package com.bills.billingsoftware.repository;

import com.bills.billingsoftware.entity.Invoice;
import com.bills.billingsoftware.service.InvoiceService;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InvoiceRepository {

    private final List<Invoice> invoices = new ArrayList<>();

    public void save(Invoice invoice) {
        invoices.add(invoice);
    }

    public List<Invoice> findAll() {
        return invoices;
    }
}
