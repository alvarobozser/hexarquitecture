package com.paymentchain.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.paymentchain.customer.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	@Query("Select c FROM Customer c WHERE c.code=?1")
	public Customer findByCode(String code);
	
	@Query("Select c FROM Customer c WHERE c.iban=?1")
	public Customer findByIban(String iban);
}
