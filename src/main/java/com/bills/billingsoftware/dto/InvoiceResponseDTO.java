package com.bills.billingsoftware.dto;

import com.bills.billingsoftware.entity.Invoice;

public class InvoiceResponseDTO {
    private Invoice invoice;
    private String message;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
