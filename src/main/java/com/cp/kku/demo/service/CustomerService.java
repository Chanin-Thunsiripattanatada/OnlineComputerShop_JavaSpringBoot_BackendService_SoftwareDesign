package com.cp.kku.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.kku.demo.model.Customer;
import com.cp.kku.demo.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return (List<Customer>)customerRepository.findAll();
    }

    public Customer getCustomerById(int id) {
        Customer customer = customerRepository.findById(id)
	      	      .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
		return customer;
    }

    public Customer getCustomerByUserId(int id) {
        Customer customer = customerRepository.findByUserId(id)
	      	      .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
		return customer;
    }
    

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Customer updateCustomer(int id, Customer customer) {
		Customer existingCustomer = customerRepository.findById(id).get();
		existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setBillingAddress(customer.getBillingAddress());
        existingCustomer.setShippingAddress(customer.getShippingAddress());
	    return customerRepository.save(existingCustomer);
	}

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }
}
