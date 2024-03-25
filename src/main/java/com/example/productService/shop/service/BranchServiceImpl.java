package com.example.productService.shop.service;

import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.shop.Branch;
import com.example.productService.repository.shop.BranchRepository;
import com.example.productService.shop.dto.BranchResponse;
import com.example.productService.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BranchServiceImpl {
    private final BranchRepository branchRepository;

    public BranchResponse getBranchById(Long branchId){
        Optional<Branch> branch = branchRepository.findById(branchId);
        if(branch.isEmpty()){
            throw new NotFoundResponseException("Not found branch with id "+ branchId);
        }
        return ModelMapper.ConvertBranchDTO(branch.get());
    }

    public List<BranchResponse> getBranchesByIds(List<Long> branchIds){
        List<Branch> branches = branchIds.stream().map(id ->{
            Optional<Branch> branch=  branchRepository.findById(id);
            if(branch.isEmpty()){
                throw new NotFoundResponseException("Not found branch with id "+id);
            }
            return branch.get();
        }).toList();

        return branches.stream().map(ModelMapper::ConvertBranchDTO).collect(Collectors.toList());
    }
}
