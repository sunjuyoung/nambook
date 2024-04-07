package com.example.springApi.service;

import com.example.springApi.domain.Product;
import com.example.springApi.domain.ProductImage;
import com.example.springApi.dto.PageRequestDTO;
import com.example.springApi.dto.PageResponseDTO;
import com.example.springApi.dto.ProductDTO;
import com.example.springApi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{


    private final ProductRepository productRepository;

    @Override
    public ProductDTO get(Long id) {
        Product product = productRepository.selectOne(id).orElseThrow();
        ProductDTO productDTO = entityToDTO(product);


        return productDTO;
    }

    private ProductDTO entityToDTO(Product product) {

        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .pname(product.getPname())
                .pdesc(product.getPdesc())
                .price(product.getPrice())
                .build();

        List<ProductImage> productImages = product.getImageList();
        if(productImages == null || productImages.size() == 0){
            return productDTO;
        }
        List<String> imageStrings = productImages.stream().map(productImage -> {
            return productImage.getFileName();
        }).collect(Collectors.toList());
        productDTO.setUploadFileNames(imageStrings);
        return productDTO;
    }

    @Override
    public Long register(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);
        Product saveProduct = productRepository.save(product);
        StringTokenizer st = new StringTokenizer(productDTO.getPname(), ",");
        st.nextToken();
        return saveProduct.getId();
    }

    private Product dtoToEntity(ProductDTO productDTO) {
        Product product = Product.builder()
                .pname(productDTO.getPname())
                .pdesc(productDTO.getPdesc())
                .price(productDTO.getPrice())
                .build();
        List<String> uploadFileNames = productDTO.getUploadFileNames();

        if(uploadFileNames == null){
            return product;
        }
        uploadFileNames.stream().forEach(fileName -> {
            product.addImageString(fileName);
        });

        return product;
    }

    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize());
        Page<Object[]> result = productRepository.listWithImages(pageable);
        List<ProductDTO> productDTOList = result.get().map(arr -> {
            Product product = (Product) arr[0];
            ProductImage productImage = (ProductImage) arr[1];
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .pname(product.getPname())
                    .pdesc(product.getPdesc())
                    .price(product.getPrice())
                    .build();

            String imageStr = productImage.getFileName();
            productDTO.setUploadFileNames(Arrays.asList(imageStr));
            return productDTO;
        }).collect(Collectors.toList());

        return PageResponseDTO.<ProductDTO>withAll()
                .dtoList(productDTOList)
                .totalCount(result.getTotalElements())
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
