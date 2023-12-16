package com.example.springApi.repository;

import com.example.springApi.domain.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    void testInsert(){

        for(int i=0; i<100; i++){

            Product product = Product.builder()
                    .price(1000+i)
                    .pname("상품"+i)
                    .pdesc("상품"+i+"입니다.")
                    .build();

            product.addImageString(UUID.randomUUID().toString()+"_"+"test1.jpg");
            product.addImageString(UUID.randomUUID().toString()+"_"+"test2.jpg");
            productRepository.save(product);
        }

    }

    @Transactional
    @Test
    void testRead(){
        Product product = productRepository.findById(1L).orElseThrow();
        log.info(product);
        log.info(product.getImageList());

    }

    @Test
    void testRead2(){
        Product product = productRepository.selectOne(1L).orElseThrow();
        log.info(product);
        log.info(product.getImageList());
    }


}