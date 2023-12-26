package com.example.productService.repository;

import com.example.productService.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop,Long> {

}
