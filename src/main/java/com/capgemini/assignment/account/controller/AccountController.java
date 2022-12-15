package com.capgemini.assignment.account.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.assignment.account.entity.Account;
import com.capgemini.assignment.account.entity.Customer;
import com.capgemini.assignment.account.entity.Transaction;
import com.capgemini.assignment.account.model.AccountModel;
import com.capgemini.assignment.account.service.AccountService;
import com.capgemini.assignment.account.service.CustomerService;
import com.capgemini.assignment.account.service.TransactionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class AccountController {

	private final AccountService accountService;
	private final CustomerService customerService;
	private final TransactionService transactionService;
	@Autowired
	public AccountController(AccountService accountService,CustomerService customerService,TransactionService transactionService) {
		this.accountService = accountService;
		this.customerService=customerService;
		this.transactionService = transactionService;
	}

	@PostMapping("/account")
	public ResponseEntity<?> addAccount(@RequestBody AccountModel accountModel) {
		if(accountModel == null || accountModel.checkIfAnyValueMissing()) {
			return responseEntityWithMessage("CustomerId and initialCredit are mondatory");
		}
		if(accountModel.getInitialCredit().compareTo(BigDecimal.ZERO) < 0) {
			return responseEntityWithMessage("InitialCredit cannot be negative");
		}
		Optional<Customer> customer = customerService.findCustomer(accountModel.getCustomerID());
		if(customer.isEmpty()) {
			return responseEntityWithMessage("CustomerId provided cannnot be found");
		}
		Account account = new Account();
		account.setCustomer(customer.get());
		account.setBalance(accountModel.getInitialCredit());
		account.setCreationDate(LocalDate.now());

		account = accountService.addNewAccount(account);
		createTransaction(accountModel, account);
		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}

	private void createTransaction(AccountModel accountModel, Account account) {
		if(accountModel.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
			Transaction transaction= new Transaction();
			transaction.setAccount(account);
			transaction.setTransactionDate(LocalDate.now());
			transaction.setTransactionAmount(accountModel.getInitialCredit());
			transactionService.addNewTransaction(transaction);
			account.setTransaction(Arrays.asList(transaction));
		}		
	}

	private ResponseEntity<?> responseEntityWithMessage(String message) {
		
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	

	@GetMapping("/account/{customerId}")
	public ResponseEntity<?> retriveAllStudents(@PathVariable String customerId) {
		Optional<Customer> customer = customerService.findCustomer(customerId);
		if(customer.isEmpty()) {
			return responseEntityWithMessage("CustomerId provided cannnot be found");
		}
		return new ResponseEntity<>(accountService.findByCustomer(customer.get()), HttpStatus.OK);
	}
}
