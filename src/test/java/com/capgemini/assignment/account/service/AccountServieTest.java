package com.capgemini.assignment.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.capgemini.assignment.account.entity.Account;
import com.capgemini.assignment.account.entity.Customer;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class AccountServieTest {

	@Autowired
	private  AccountService accountService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Test
	@Order(1)
	void findAllAccountWithoutResult() {
		transactionService.deleteAllTransaction();
		accountService.deleteAllAccount();
		List<Account> accountList =accountService.findAllAccount();
		assertThat(accountList).isEmpty();
	}
	@Test
	@Order(2)
	void addNewAccountTest() {
		Customer customer = new Customer();
		Account account = new Account();
		creatingNewAccount(account,customer);

	}
	@Test()
	@Order(3)
	void addNewAccountWrongCustomerTest() {
		//Customer doesnt exist on db
		Customer customer = new Customer();
		customer.setName("Jhon");
		customer.setSurname("Philipe");
		customer.setId("536c66d7-4aae-4dd2-b87b-9ce7682bd67f536c66d7-4aae-4dd2-b87b-9ce7682bd67f");
		Account account = new Account();
		assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(
				() -> {newAccount(account, customer);}
				);
	}
	
	@Test
	@Order(4)
	void findExistingAccount() {
		Customer customer = new Customer();
		Account account = new Account();
		creatingNewAccount(account,customer);
		Optional<Account> resAccount =accountService.findAccount(account.getId());
		assertThat(resAccount).isPresent();
		assertThat(resAccount.get().getCustomer()).isNotNull();
		assertThat(resAccount.get().getCustomer().getName()).isEqualTo("Jhon");
	}
	@Test
	@Order(5)
	void findNonExistingAccount() {
		Optional<Account> resAccount =accountService.findAccount("536c66d7-4aae-4dd2-b87b-9ce7682bd67f536c66d7-4aae-4dd2-b87b-9ce7682bd67f");
		assertThat(resAccount).isEmpty();
	}
	@Test
	@Order(6)
	void findAllAccountWithResult() {
		List<Account> accountList =accountService.findAllAccount();
		assertThat(accountList).isNotNull().hasSizeGreaterThan(0);
	}
	
	@Test
	@Order(7)
	void updateAccountBalance() {
		List<Account> accountList =accountService.findAllAccount();
		assertThat(accountList).isNotEmpty();
		Account account = accountList.get(0);
		account.setBalance(new BigDecimal(50));
		accountService.updateAccount(account);
		assertThat(account.getBalance()).isEqualTo(new BigDecimal(50));
	}
	
	@Test
	@Order(8)
	void deleteAccountById() {
		List<Account> accountList =accountService.findAllAccount();
		assertThat(accountList).isNotEmpty();
		Account account = accountList.get(0);
		if(!account.getTransaction().isEmpty())
			transactionService.deleteTransaction(account.getTransaction().get(0).getId());
		accountService.deleteAccount(account.getId());
		Optional<Account> deletedAccount= accountService.findAccount(account.getId());
		assertThat(deletedAccount).isEmpty();
	}
	
	private void creatingNewAccount(Account account,Customer customer) {
		newCustomer(customer);
		assertThat(customer).isNotNull();
		account = newAccount(account ,customer);
		assertThat(account).isNotNull();
	}

	private Customer newCustomer(Customer customer) {
		customer.setName("Jhon");
		customer.setSurname("Philipe");
		return customerService.addNewCustomer(customer);
	}

	private Account newAccount(Account account, Customer customer) {
		account.setCustomer(customer);
		account.setBalance(new BigDecimal(0));
		account.setCreationDate(LocalDate.now());
		return accountService.addNewAccount(account);
	}
	
	
}
