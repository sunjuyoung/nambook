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
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
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


    //실제 물리적인 삭제 대신에 특정한 칼럼의 값을 기준으로 해당 상품이
    //삭제되었는지 구분 , delete 대신 update를 사용
    public void changeDel(boolean delFlag){
        this.delFlag = delFlag;
    }




}
