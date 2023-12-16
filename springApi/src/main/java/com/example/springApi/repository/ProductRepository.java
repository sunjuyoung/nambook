package com.example.springApi.repository;

import com.example.springApi.domain.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.id = :id")
    Optional<Product> selectOne(@Param("id") Long id);

}
