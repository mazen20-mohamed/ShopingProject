package com.example.productService.shop.controller;

import com.example.productService.exception.ErrorResponse;
import com.example.productService.shop.dto.BranchResponse;
import com.example.productService.shop.service.BranchServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/branch")
@RequiredArgsConstructor
@Tag(name = "Branch")
public class BranchController {
    private final BranchServiceImpl branchService;

    @GetMapping("/{branchId}")
    @Operation(summary = "get branch by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "branch id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public BranchResponse getBranchById(@PathVariable Long branchId){
        return branchService.getBranchById(branchId);
    }

    @Operation(summary = "get branches by ids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "one of branch id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @PostMapping
    public List<BranchResponse> getBranchesByIds(@RequestBody List<Long> branchIds){
        return branchService.getBranchesByIds(branchIds);
    }
}
