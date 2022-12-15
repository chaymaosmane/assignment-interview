package com.capgemini.assignment.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.assignment.account.entity.Account;
import com.capgemini.assignment.account.entity.Customer;
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	public List<Account> findByCustomer(Customer customer);
}
