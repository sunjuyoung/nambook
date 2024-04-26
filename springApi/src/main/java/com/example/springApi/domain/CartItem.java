package com.example.springApi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "cart_item", indexes = {
        @Index(name = "idx_cart_item_cno", columnList = "cno"),
        @Index(name = "idx_cart_item_product_id", columnList = "product_id")
})
public class CartItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cno")
    private Cart cart;

    private int quantity;

    public void changeQuantity(int quantity){
        this.quantity = quantity;
    }
}
