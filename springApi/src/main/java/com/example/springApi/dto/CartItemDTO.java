package com.example.springApi.dto;

import lombok.Data;

@Data
public class CartItemDTO {

    private String email;
    private Long productId;
    private int quantity;
    private Long cno;
}
