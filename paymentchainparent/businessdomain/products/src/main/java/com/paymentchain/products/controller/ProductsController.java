package com.paymentchain.products.controller;
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
import org.springframework.web.bind.annotation.RestController;

import com.paymentchain.products.entities.Products;
import com.paymentchain.products.repository.ProductsRepository;

@RestController
@RequestMapping("/products") 
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;
    
    @GetMapping()
    public List<Products> list() {
        return productsRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
         Optional<Products> customer = productsRepository.findById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Products input) {
        return productsRepository.findById(id)
            .map(existingProduct -> {
            	existingProduct.setName(input.getName());
            	existingProduct.setCode(input.getCode());
                Products saved = productsRepository.save(existingProduct);
                return new ResponseEntity<>(saved, HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Products input) {
    	Products save = productsRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
    	productsRepository.deleteById(id);
         return new ResponseEntity<>(HttpStatus.OK);
    }
}
