package com.paymentchain.transactions.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentchain.transactions.entities.Transactions;
import com.paymentchain.transactions.repository.TransactionsRepository;

@RestController
@RequestMapping("/transactions") 
public class TransactionsController {

    @Autowired
    private TransactionsRepository transactionsRepository;
    
    @GetMapping()
    public List<Transactions> list() {
        return transactionsRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
         Optional<Transactions> transaction = transactionsRepository.findById(id);
        if (transaction.isPresent()) {
            return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/getAccount")
    public List<Transactions> getTransactionByaccountIban(@RequestParam String iban) {
        return transactionsRepository.findTransactionByaccountIban(iban);  
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable long id, @RequestBody Transactions input) {
        return transactionsRepository.findById(id)
                .map(existingTransaction -> {
                    existingTransaction.setReference(input.getReference());
                    existingTransaction.setAccountIban(input.getAccountIban());
                    existingTransaction.setDate(input.getDate());
                    existingTransaction.setAmount(input.getAmount());
                    existingTransaction.setFee(input.getFee());
                    existingTransaction.setDescription(input.getDescription());
                    existingTransaction.setStatus(input.getStatus());
                    existingTransaction.setChannel(input.getChannel());
                    
                    Transactions saved = transactionsRepository.save(existingTransaction);
                    return new ResponseEntity<>(saved, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Transactions input) {
    	Transactions save = transactionsRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
    	transactionsRepository.deleteById(id);
         return new ResponseEntity<>(HttpStatus.OK);
    }
}