package com.paymentchain.transactions.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Transactions {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reference;
    private String accountIban;
    private LocalDateTime date;
    private double amount;
    private double fee;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Enumerated(EnumType.STRING)
    private Channel channel;
    
    public enum Status {
        PENDIENTE("01"),
        LIQUIDADA("02"),
        RECHAZADA("03"),
        CANCELADA("04");
        
        private String code;
        
        Status(String code) {
            this.code = code;
        }
        
        public String getCode() {
            return code;
        }
    }
    
    public enum Channel {
        WEB,
        CAJERO,
        OFICINA
    }
}