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
@Table(name = "cart", indexes = {
        @Index(name = "idx_cart_email", columnList = "email")}
)
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cno")
    private Long cno;

    @OneToOne
    @JoinColumn(name = "email")
    private Member owner;
}
