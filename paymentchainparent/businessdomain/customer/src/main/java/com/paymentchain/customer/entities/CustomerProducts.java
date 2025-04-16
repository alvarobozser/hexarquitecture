package com.paymentchain.customer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class CustomerProducts {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	
	private Long producId;	
	@Transient
	private String productName;
	@JsonIgnore
	@ManyToOne(fetch =FetchType.LAZY, targetEntity=Customer.class)
	@JoinColumn(name="customerId",nullable=true)
	private Customer customer;
}
