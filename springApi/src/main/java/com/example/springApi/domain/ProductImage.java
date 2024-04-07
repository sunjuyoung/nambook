package com.example.springApi.domain;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    private String fileName;

    //각 이미지의 번호를 지정하고, 0번인 이미지들만 화면에서 볼 수 있도록 하기 위함
    private int ord;

    public void setOrd(int ord){
        this.ord = ord;
    }
}
