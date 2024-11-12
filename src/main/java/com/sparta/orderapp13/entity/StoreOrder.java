//package com.sparta.orderapp13.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.UuidGenerator;
//
//import java.util.UUID;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//public class StoreOrder {
//
//    @Id
//    @GeneratedValue
//    @UuidGenerator
//    @Column(columnDefinition = "BINARY(16)")
//    private UUID storeOrderId;
//
//    @ManyToOne
//    @JoinColumn
//    private Order order;
//
//    @ManyToOne
//    @JoinColumn
//    private Store store;
//
//}
