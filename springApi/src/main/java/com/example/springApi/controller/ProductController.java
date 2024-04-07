package com.example.springApi.controller;

import com.example.springApi.dto.PageRequestDTO;
import com.example.springApi.dto.PageResponseDTO;
import com.example.springApi.dto.ProductDTO;
import com.example.springApi.service.ProductService;
import com.example.springApi.util.CustomFileUtil;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;


    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable Long id){
        return productService.get(id);
    }

    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO){

        //data type String check


        return productService.getList(pageRequestDTO);
    }


    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName){
        return fileUtil.getFile(fileName);
    }


    @PostMapping("/")
    public Map<String,Long> register(ProductDTO productDTO) throws Exception{

        List<MultipartFile> files = productDTO.getFiles();

        List<String> strings = fileUtil.saveFiles(files);

        productDTO.setUploadFileNames(strings);

        Long register = productService.register(productDTO);

        return Map.of("result",register);
    }
}
