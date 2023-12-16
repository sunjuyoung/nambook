package com.example.springApi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq")
    @Column(name = "product_id")
    private Long id;

    private String pname;

    private int price;

    private String pdesc;

    private boolean delFlag;

    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();

    public void changePrice(int price){
        this.price = price;
    }

    public void changeName(String pname){
        this.pname = pname;
    }

    public void addImage(ProductImage productImage){
        imageList.add(productImage);
    }

    public void addImageString(String fileName){
        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();
        addImage(productImage);
    }

    public void clearList(){
        this.imageList.clear();
    }





}
