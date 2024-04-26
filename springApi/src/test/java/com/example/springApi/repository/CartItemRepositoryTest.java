package com.example.springApi.repository;

import com.example.springApi.dto.CartItemListDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    void getCartItemListByEmail() {
        String email = "user11@aaa.com";
        List<CartItemListDTO> itemCartListByEmail = cartItemRepository.getItemCartListByEmail(email);
        Assertions.assertEquals(0, itemCartListByEmail.size());

    }

    @Test
    void getCartItemByProductAndEmail() {
        String email = "user11@aaa.com";
       //cartItemRepository.getCartItemByProductAndEmail(email, 1L);

       cartItemRepository.getItemCartListByCno(1L);
    }

}