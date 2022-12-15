package com.capgemini.assignment.account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.assignment.account.entity.Transaction;
import com.capgemini.assignment.account.repository.TransactionRepository;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;

	@Autowired
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public Transaction addNewTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	// find existing record
	public Optional<Transaction> findTransaction(String transactionId) {
		return transactionRepository.findById(transactionId);
	}

	// find All records
	public List<Transaction> findAllTransaction() {
		return transactionRepository.findAll();
	}

	// updating existing record
	public Transaction updateTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	// deleting existing record
	public Boolean deleteTransaction(String custumerId) {
		transactionRepository.deleteById(custumerId);
		return true;
	}

	// deleting existing record
	public Boolean deleteAllTransaction() {
		transactionRepository.deleteAll();
		return true;
	}

}
