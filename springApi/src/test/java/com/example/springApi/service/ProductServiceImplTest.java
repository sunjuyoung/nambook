package com.example.springApi.service;

import com.example.springApi.dto.PageRequestDTO;
import com.example.springApi.dto.PageResponseDTO;
import com.example.springApi.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class ProductServiceImplTest {


    @Autowired
    private ProductService productService;

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