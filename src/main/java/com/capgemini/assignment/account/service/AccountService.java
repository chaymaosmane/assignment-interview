package com.capgemini.assignment.account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.assignment.account.entity.Account;
import com.capgemini.assignment.account.entity.Customer;
import com.capgemini.assignment.account.repository.AccountRepository;

@Service
public class AccountService {
	private final AccountRepository accountRepository;

	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Account addNewAccount(Account account) {
		return accountRepository.save(account);
	}

	// find existing record
	public Optional<Account> findAccount(String accountId) {
		return accountRepository.findById(accountId);
	}

	// find All records
	public List<Account> findAllAccount() {
		return accountRepository.findAll();
	}

	// updating existing record
	public Account updateAccount(Account account) {
		return accountRepository.save(account);
	}

	// deleting existing record
	public Boolean deleteAccount(String id) {
		accountRepository.deleteById(id);
		return true;
	}

	// deleting existing record
	public Boolean deleteAllAccount() {
		accountRepository.deleteAll();
		return true;
	}

	public List<Account> findByCustomer(Customer custmer) {
		return accountRepository.findByCustomer(custmer);
	}
}
