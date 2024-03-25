package com.example.productService.repository.shop;

import com.example.productService.model.shop.Branch;
import com.example.productService.model.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch,Long> {
    @Query("SELECT DISTINCT p from Branch p where p.city = :city AND p.shop.enabled = true")
    Page<Branch> findByCity(String city, Pageable pageable);
    @Query("SELECT DISTINCT p from Branch p where p.government = :government AND p.shop.enabled = true")
    Page<Branch> findByGovernment(String government , Pageable pageable);
}
