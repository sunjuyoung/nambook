package com.example.springApi.service;

import com.example.springApi.dto.PageRequestDTO;
import com.example.springApi.dto.PageResponseDTO;
import com.example.springApi.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class ProductServiceImplTest {


    @Autowired
    private ProductService productService;

    @Test
    void get() {
        ProductDTO productDTO = productService.get(22L);
        log.info(productDTO);
    }

    @Test
    void register() {
        List<String> uploadFileNames = Arrays.asList("test1.jpg","test2.jpg","test3.jpg");
        ProductDTO productDTO = ProductDTO.builder()
                .pname("테스트 상품")
                .pdesc("테스트 상품 설명")
                .price(10000)
                .uploadFileNames(uploadFileNames)
                .build();


        Long id = productService.register(productDTO);
        log.info(id);
    }

    @Test
    void getList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<ProductDTO> list = productService.getList(pageRequestDTO);
        list.getDtoList().forEach(productDTO -> {
            log.info(productDTO);
        });
    }

}