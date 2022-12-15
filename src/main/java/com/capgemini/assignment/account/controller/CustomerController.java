package com.capgemini.assignment.account.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.assignment.account.entity.Customer;
import com.capgemini.assignment.account.service.CustomerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class CustomerController {
	private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
    	this.customerService =customerService;
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.addNewCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> retriveCustomer(@PathVariable String id) {
    	Optional<Customer> customer = customerService.findCustomer(id);
    	if(customer.isPresent()) {
    		return new ResponseEntity<>(customer.get(), HttpStatus.OK);	
    	}else {
    		return new ResponseEntity<>("Customer is not found", HttpStatus.NOT_FOUND);
    	}
        
    }

    @PutMapping("/customer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable String id) {
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }

    @GetMapping("/customer/initialise")
    public List<Customer> initialiseSomeDate(){
    	List<Customer> customerList = new ArrayList<>();
    	Customer c1 = new Customer();
    	c1.setName("Philipe");
    	c1.setSurname("Antoine");
    	Customer c2 = new Customer();
    	c2.setName("Luc");
    	c2.setSurname("Vandervan");
    	Customer c3 = new Customer();
    	c3.setName("Cedric");
    	c3.setSurname("Deluchi");
    	customerList.add(customerService.addNewCustomer(c1));
    	customerList.add(customerService.addNewCustomer(c2));
    	customerList.add(customerService.addNewCustomer(c3));
    	return customerList;
    }
    @GetMapping("/customer")
    public List<Customer> getAllCustomer(){
    	return customerService.findAllCustomer();
    }
}
