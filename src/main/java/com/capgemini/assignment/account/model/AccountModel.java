package com.capgemini.assignment.account.model;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {
	private String customerID;
	private BigDecimal initialCredit;
	
	public Boolean checkIfAnyValueMissing() {
		return !StringUtils.hasLength(customerID)  || initialCredit == null ;
	}
}
