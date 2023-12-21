package com.example.springApi.repository;

import com.example.springApi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.id = :id")
    Optional<Product> selectOne(@Param("id") Long id);

    @Modifying
    @Query("update Product p set p.delFlag = :flag where p.id = :id")
    void updateToDelete(@Param("id") Long id, @Param("flag") boolean flag);

    @Query("select p,pi from Product p left join p.imageList pi where pi.ord = 0 and p.delFlag = false ")
    Page<Object[]> listWithImages(Pageable pageable);
}
