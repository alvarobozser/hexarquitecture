package com.paymentchain.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentchain.products.entities.Products;

public interface ProductsRepository extends JpaRepository<Products, Long>{
}
