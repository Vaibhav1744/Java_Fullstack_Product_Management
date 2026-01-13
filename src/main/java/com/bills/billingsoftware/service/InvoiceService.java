package com.bills.billingsoftware.service;
import com.bills.billingsoftware.dto.InvoiceRequestDTO;
import com.bills.billingsoftware.entity.Customer;
import com.bills.billingsoftware.entity.Invoice;
import com.bills.billingsoftware.entity.InvoiceItem;
import com.bills.billingsoftware.entity.Product;
import com.bills.billingsoftware.exception.InsufficientStockException;
import com.bills.billingsoftware.exception.ResourceNotFoundException;
import com.bills.billingsoftware.repository.CustomerRepository;
import com.bills.billingsoftware.repository.InvoiceRepository;
import com.bills.billingsoftware.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    private final ProductRepository productRepo;
    private final CustomerRepository customerRepo;
    private final InvoiceRepository invoiceRepo;

    private long invoiceCounter = 1000;

    public InvoiceService(ProductRepository p, CustomerRepository c, InvoiceRepository i) {
        this.productRepo = p;
        this.customerRepo = c;
        this.invoiceRepo = i;
    }

    public Invoice generateInvoice(InvoiceRequestDTO request) {

        Customer customer = customerRepo.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        List<InvoiceItem> items = new ArrayList<>();
        double totalAmount = 0;
        double totalTax = 0;

        for (Map.Entry<Long, Integer> entry : request.getProductQuantities().entrySet()) {

            Product product = productRepo.findById(entry.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            int qty = entry.getValue();

            if (product.getStockQuantity() < qty) {
                throw new InsufficientStockException("Insufficient stock for " + product.getName());
            }

            double price = product.getPrice() * qty;
            double tax = price * product.getGstPercentage() / 100;

            InvoiceItem item = new InvoiceItem();
            item.setProduct(product);
            item.setQuantity(qty);
            item.setPrice(price);
            item.setTaxAmount(tax);
            item.setTotal(price + tax);

            items.add(item);

            totalAmount += price;
            totalTax += tax;

            product.setStockQuantity(product.getStockQuantity() - qty);
            productRepo.save(product);
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(++invoiceCounter);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setCustomer(customer);
        invoice.setItems(items);
        invoice.setTotalAmount(totalAmount);
        invoice.setTotalTax(totalTax);
        invoice.setDiscount(request.getDiscount());
        invoice.setFinalAmount(totalAmount + totalTax - request.getDiscount());

        invoiceRepo.save(invoice);
        return invoice;
    }
}
