package com.example.springApi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CartItemListDTO {

    private Long cino;

    private int quantity;

    private Long productId;

    private String pname;

    private int price;

    private String imageFile;

    public CartItemListDTO(Long cino, int quantity, Long productId, String pname, int price, String imageFile){
        this.cino = cino;
        this.quantity = quantity;
        this.productId = productId;
        this.pname = pname;
        this.price = price;
        this.imageFile = imageFile;
    }
}
