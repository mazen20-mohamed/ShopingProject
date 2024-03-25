package com.example.productService.shop.controller;

import com.example.productService.shop.dto.BranchResponse;
import com.example.productService.shop.service.BranchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/branch")
@RequiredArgsConstructor
public class BranchController {
    private final BranchServiceImpl branchService;

    @GetMapping("/{branchId}")
    public BranchResponse getBranchById(@PathVariable Long branchId){
        return branchService.getBranchById(branchId);
    }

    @PostMapping
    public List<BranchResponse> getBranchesByIds(@RequestBody List<Long> branchIds){
        return branchService.getBranchesByIds(branchIds);
    }
}
