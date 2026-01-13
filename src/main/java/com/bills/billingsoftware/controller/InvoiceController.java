package com.bills.billingsoftware.controller;

import com.bills.billingsoftware.dto.InvoiceRequestDTO;
import com.bills.billingsoftware.entity.Invoice;
import com.bills.billingsoftware.service.InvoiceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService service) {
        this.invoiceService = service;
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody InvoiceRequestDTO request) {
        return invoiceService.generateInvoice(request);
    }
}
