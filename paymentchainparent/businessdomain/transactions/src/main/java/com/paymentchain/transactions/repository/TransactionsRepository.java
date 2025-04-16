package com.paymentchain.transactions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.paymentchain.transactions.entities.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Long>{
	@Query("Select c FROM Transactions c WHERE c.accountIban=?1")
	List<Transactions> findTransactionByaccountIban(String iban);
}


