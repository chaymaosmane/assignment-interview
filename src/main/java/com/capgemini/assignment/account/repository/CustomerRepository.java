package com.capgemini.assignment.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.assignment.account.entity.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
