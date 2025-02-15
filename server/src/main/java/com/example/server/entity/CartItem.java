package com.example.server.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.example.server.util.CartIdSerializer;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cart_items")
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @JsonSerialize(using = CartIdSerializer.class)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;
}
