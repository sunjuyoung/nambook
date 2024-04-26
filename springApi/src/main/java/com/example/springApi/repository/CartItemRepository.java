package com.example.springApi.repository;

import com.example.springApi.domain.CartItem;
import com.example.springApi.dto.CartItemListDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {



    //이메일을 통해서 해당 사용자의 모든 장바구니 아이템을 조회
    @Query("select new com.example.springApi.dto.CartItemListDTO(" +
            " ci.cino, ci.quantity, p.id, p.pname, p.price, pi.fileName) " +
            " from CartItem ci inner join Cart c on ci.cart = c" +
            " left join ci.product p" +
            " left join p.imageList pi " +
            " where c.owner.email = :email")
    List<CartItemListDTO> getItemCartListByEmail(@Param("email") String email);


    //새로운 상품을 장바구니 담을때 이미 장바구니에 있는 상품인지 이메일,상품아이디 확인
    @Query("select ci " +
            " from CartItem ci inner join Cart c on ci.cart = c " +
            " where c.owner.email = :email and ci.product.id = :product_id")
    CartItem getCartItemByProductAndEmail(@Param("email") String email, @Param("product_id") Long product_id);


    //장바구니아이템 삭제시 장바구니 번호를 통해서 장바구니 조회
    @Query("select " +
            "  c.cno " +
            "from " +
            "  Cart c inner join CartItem ci on ci.cart = c " +
            " where " +
            "  ci.cino = :cino")
    public Long getCartFromItem( @Param("cino") Long cino);


    //장바구니 번호를 통해서 모든 장바구니 아이템 조회
    @Query("select new com.example.springApi.dto.CartItemListDTO(" +
            " ci.cino, ci.quantity, p.id, p.pname, p.price, pi.fileName) " +
            " from CartItem ci inner join Cart c on ci.cart = c" +
            " left join ci.product p" +
            " left join p.imageList pi " +
            " where c.cno = :cno")
    List<CartItemListDTO> getItemCartListByCno(@Param("cno") Long cno);


}
