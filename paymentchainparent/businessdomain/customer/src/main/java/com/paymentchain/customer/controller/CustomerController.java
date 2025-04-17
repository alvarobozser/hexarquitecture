package com.paymentchain.customer.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.repository.CustomerRepository;
import com.paymentchain.customer.transactions.BusinessTransactions;

@RestController
@RequestMapping("/customer") 
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private BusinessTransactions businessTransactions;
    
    @Autowired
    private Environment environment;
    
    
    @GetMapping("/check")
    public String check() {
        return environment.getProperty("customer.activeprofileName");
    }
    
    @GetMapping()
    public ResponseEntity<List<Customer>> list() {
         List<Customer> all = customerRepository.findAll();
        if (!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }   
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
         Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Customer input) {
        return customerRepository.findById(id)
            .map(existingCustomer -> {
                existingCustomer.setName(input.getName());
                existingCustomer.setPhone(input.getPhone());
                Customer saved = customerRepository.save(existingCustomer);
                return new ResponseEntity<>(saved, HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Customer input) { 	
    	input.getProducts().forEach(product -> product.setCustomer(input));  	
        Customer save = customerRepository.save(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
         customerRepository.deleteById(id);
         return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/full")
    public Customer getByCode(@RequestParam String code) {
    	return businessTransactions.getByCode(code);  
    }
  
}