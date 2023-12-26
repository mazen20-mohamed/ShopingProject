package com.example.productService.repository;

import com.example.productService.model.shop.Branch;
import com.example.productService.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch,Long> {
    Optional<List<Branch>> findByCity(String city);
    Optional<List<Branch>> findByGovernment(String government);
}
