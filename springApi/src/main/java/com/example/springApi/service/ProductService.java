package com.example.springApi.service;


import com.example.springApi.dto.PageRequestDTO;
import com.example.springApi.dto.PageResponseDTO;
import com.example.springApi.dto.ProductDTO;

public interface ProductService {

    PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(ProductDTO productDTO);

    ProductDTO get(Long id);
}
