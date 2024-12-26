package com.project.bookseller.entity;

import com.project.bookseller.entity.book.Book;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderRecordId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
    private Double price;
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_information_id")
    private OrderInformation orderInformation;
}
