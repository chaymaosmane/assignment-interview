package com.capgemini.assignment.account.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.capgemini.assignment.account.ControllerTest;
import com.capgemini.assignment.account.entity.Customer;
import com.capgemini.assignment.account.model.AccountModel;

@AutoConfigureMockMvc
@SpringBootTest()
@DirtiesContext
class AccountControllerTest extends ControllerTest {

	private Customer generateCustomer() {
		Customer customer = new Customer();
		customer.setName("Jhonny");
		customer.setSurname("maclaren");
		return customerService.addNewCustomer(customer);
	}

	@Test
	void testCreateAccountWithNoBalance() throws Exception {
		Customer customer = generateCustomer();
		AccountModel accountModel = new AccountModel();
		accountModel.setCustomerID(customer.getId());
		accountModel.setInitialCredit(new BigDecimal(0));
		this.mockMvc
				.perform(post("/api/v1/account").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(accountModel)))
				.andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.balance", equalTo(0)))
                .andExpect(jsonPath("$.customer.id", equalTo(customer.getId())))
                .andExpect(jsonPath("$.customer.name", equalTo(customer.getName())))
                .andExpect(jsonPath("$.customer.surname", equalTo(customer.getSurname())))
                .andExpect(jsonPath("$.transaction", nullValue()));
	}
	
	@Test
	void testCreateAccountWithBalance() throws Exception {
		Customer customer = generateCustomer();
		AccountModel accountModel = new AccountModel();
		accountModel.setCustomerID(customer.getId());
		accountModel.setInitialCredit(new BigDecimal(50));
		this.mockMvc
				.perform(post("/api/v1/account").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(accountModel)))
				.andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.balance", equalTo(50)))
                .andExpect(jsonPath("$.customer.id", equalTo(customer.getId())))
                .andExpect(jsonPath("$.customer.name", equalTo(customer.getName())))
                .andExpect(jsonPath("$.customer.surname", equalTo(customer.getSurname())))
                .andExpect(jsonPath("$.transaction", hasSize(1)));
	}
	@Test
	void testCreateAccountWithEmptyBodyCustomerId() throws Exception {
		AccountModel accountModel = new AccountModel();
		accountModel.setInitialCredit(new BigDecimal(50));
		MvcResult result = this.mockMvc
				.perform(post("/api/v1/account").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(accountModel)))
				.andDo(MockMvcResultHandlers.print())
	            .andExpect(status().isNotFound())
	            .andReturn();
		String content = result.getResponse().getContentAsString();
		assertThat(content).isEqualTo("CustomerId and initialCredit are mondatory");
	}
	@Test
	void testCreateAccountWithEmptyBodyIitialCredit() throws Exception {
		Customer customer = generateCustomer();
		AccountModel accountModel = new AccountModel();
		accountModel.setCustomerID(customer.getId());
		MvcResult result = this.mockMvc
				.perform(post("/api/v1/account").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(accountModel)))
				.andDo(MockMvcResultHandlers.print())
	            .andExpect(status().isNotFound())
	            .andReturn();
		String content = result.getResponse().getContentAsString();
		assertThat(content).isEqualTo("CustomerId and initialCredit are mondatory");
	}
	@Test
	void testCreateAccountWithBodyNegativeIitialCredit() throws Exception {
		Customer customer = generateCustomer();
		AccountModel accountModel = new AccountModel();
		accountModel.setCustomerID(customer.getId());
		accountModel.setInitialCredit(new BigDecimal(-100));
		MvcResult result = this.mockMvc
				.perform(post("/api/v1/account").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(accountModel)))
				.andDo(MockMvcResultHandlers.print())
	            .andExpect(status().isNotFound())
	            .andReturn();
		String content = result.getResponse().getContentAsString();
		assertThat(content).isEqualTo("InitialCredit cannot be negative");
	}
	@Test
	void testCreateAccountWithWrongCustomerId() throws Exception {
		AccountModel accountModel = new AccountModel();
		accountModel.setCustomerID("999-588-788887-7777");
		accountModel.setInitialCredit(new BigDecimal(100));
		MvcResult result = this.mockMvc
				.perform(post("/api/v1/account").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(accountModel)))
				.andDo(MockMvcResultHandlers.print())
	            .andExpect(status().isNotFound())
	            .andReturn();
		String content = result.getResponse().getContentAsString();
		assertThat(content).isEqualTo("CustomerId provided cannnot be found");
	}
}
