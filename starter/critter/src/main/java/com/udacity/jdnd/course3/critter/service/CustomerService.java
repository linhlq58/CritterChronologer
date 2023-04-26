package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Long save(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
