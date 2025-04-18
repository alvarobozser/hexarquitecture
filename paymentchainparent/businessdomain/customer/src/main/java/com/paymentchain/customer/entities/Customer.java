package com.paymentchain.customer.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;


@Entity
@Data
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String code;
	private String name;
	private String phone;
	private String iban;
	private String surname;
	private String address;
	
	@OneToMany(fetch =FetchType.LAZY, mappedBy = "customer",cascade= CascadeType.ALL, orphanRemoval=true)
	private List<CustomerProducts> products;
	@Transient
	private List<?> transactions;
}