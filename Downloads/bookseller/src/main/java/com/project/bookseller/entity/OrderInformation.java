package com.project.bookseller.entity;

import com.project.bookseller.dto.OrderAddress;
import com.project.bookseller.entity.address.City;
import com.project.bookseller.entity.enums.OrderStatus;
import com.project.bookseller.entity.enums.OrderType;
import com.project.bookseller.entity.enums.PaymentMethod;
import com.project.bookseller.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class OrderInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderInformationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
    private String fullAddress;
    private String phone;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime completedAt;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ONLINE','PICKUP')")
    private OrderType orderType;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('CANCELLED','PROCESSING','COMPLETED','PENDING')")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('COD','PREPAID')")
    private PaymentMethod paymentMethod;
    private double total = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private double discount = 0;
    private String coupon;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "orderInformation", fetch = FetchType.LAZY)
    List<OrderRecord> orderRecords = new ArrayList<>();
}
