package com.bills.billingsoftware.repository;

import com.bills.billingsoftware.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.*;

@Repository
public class CustomerRepository {

    private final List<Customer> customers = new ArrayList<>();

    public Optional<Customer> findById(Long id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public void save(Customer customer) {
        customers.add(customer);
    }
}
