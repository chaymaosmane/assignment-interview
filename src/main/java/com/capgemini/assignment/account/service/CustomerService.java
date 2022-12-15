package com.capgemini.assignment.account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.assignment.account.entity.Customer;
import com.capgemini.assignment.account.repository.CustomerRepository;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;
	
	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	public Customer addNewCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	// find existing record
	public Optional<Customer> findCustomer(String custumerId) {
		return customerRepository.findById(custumerId);
	}

	// find All records
	public List<Customer> findAllCustomer() {
		return customerRepository.findAll();
	}

	// updating existing record
	public Customer updateCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	// deleting existing record
	public Boolean deleteCustomer(String custumerId) {
		customerRepository.deleteById(custumerId);
		return true;
	}
}
