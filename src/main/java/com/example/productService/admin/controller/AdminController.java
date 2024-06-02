package com.example.productService.admin.controller;

import com.example.productService.admin.dto.AppDataResponse;
import com.example.productService.admin.service.AdminService;
import com.example.productService.exception.ErrorResponse;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.shop.dto.ShopResponse;
import com.example.productService.users.dto.ManagerInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    @GetMapping("/getAllDisabledManagers/{size}/{page}")
    @Operation(summary = "find all disabled managers",description = "accessed only by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public PagedResponse<ManagerInfo> findAllManagersAreDisabled(@PathVariable int size, @PathVariable int page) {
        return adminService.findAllManagersAreDisabled(size,page);
    }
    @GetMapping("enable/{managerId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "enable manager",description = "accessed only by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "manager id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public void enableManager(@PathVariable Long managerId) {
        adminService.enableManagerToCreateShop(managerId);
    }


    public void disableManagerToCreateShop(Long managerId){
        adminService.disableManagerToCreateShop(managerId);
    }



    @GetMapping("disabledShops/{page}/{size}")
    @Operation(summary = "get all disabled shops",description = "get all disabled shops. This endpoint is only for admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public PagedResponse<ShopResponse> getAllDisabledShops(@PathVariable int size,@PathVariable int page){
        return adminService.getAllDisabledShops(size,page);
    }


    @PutMapping("disableShop/{shopId}")
    @Operation(summary = "disable a shop by id",description = "disable a shop. This endpoint is only for admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "shop id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public void disableShop(@PathVariable Long shopId){
        adminService.disableShop(shopId);
    }


    @PutMapping("enableShop/{shopId}")
    @Operation(summary = "enable a shop by id",description = "enable a shop. This endpoint is only for admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "shop id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public void enableShop(@PathVariable Long shopId){
        adminService.enableShop(shopId);
    }


    @GetMapping()
    public AppDataResponse numberOfUsers(){
        return adminService.dataResponse();
    }
}
