package com.example.productService.shop.controller;

import com.example.productService.config.CurrentUser;
import com.example.productService.exception.ErrorResponse;
import com.example.productService.model.auth.User;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.shop.dto.ShopRequest;
import com.example.productService.shop.dto.ShopResponse;
import com.example.productService.shop.dto.ShopSearchResponse;
import com.example.productService.shop.service.ShopServiceImpl;
import com.example.productService.users.dto.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/shop")
@RequiredArgsConstructor
@Tag(name = "Shop")
public class ShopController {
    private final ShopServiceImpl shopService;
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create shop with branches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "400 Bad Request",
                    description = "if there is error with input missing, shop has created before, or disabled manager to create",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public Long addShopWithBranches(@Valid @RequestBody ShopRequest shopRequest,
                                    @CurrentUser User user) {
        return shopService.addStoreWithBranches(shopRequest,user);
    }
    @Operation(summary = "add photo to shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "shop id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @PostMapping("/{shopId}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public void addPhotoForShop(@PathVariable Long shopId,
                                @NotNull @RequestParam("file") MultipartFile file)
            throws IOException {
        shopService.addPhotoShop(file,shopId);
    }
    @Operation(summary = "get shop by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "shop id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping("/{shopId}")
    public ShopResponse getShopById(@PathVariable Long shopId) {
        return shopService.getShopById(shopId);
    }
    @Operation(summary = "get shop by city name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping("/city/{city}/{page}/{size}")
    public PagedResponse<ShopResponse> getShopByCity(@PathVariable String city,
                                                     @PathVariable int page,
                                                     @PathVariable int size ) {
        return shopService.getShopByCity(city,page,size);
    }

    @Operation(summary = "get shop by government name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping("government/{government}/{page}/{size}")
    public PagedResponse<ShopResponse> getShopByGovernment(@PathVariable String government,
                                                           @PathVariable int page,
                                                           @PathVariable int size) {
        return shopService.getShopByGovernment(government,page,size);
    }
    @Operation(summary = "get manager of a shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "shop id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping("/manager/{shopId}")
    public UserInfoResponse getManagerOfShop(@PathVariable Long shopId){
        return shopService.getManagerOfShop(shopId);
    }


    @GetMapping("/photo/{fileName:.+}")
    public ResponseEntity<Resource> getShopPhoto(@PathVariable String fileName,
                                                 HttpServletRequest request){
        return shopService.getShopPhoto(fileName,request);
    }

    @PutMapping("/follow/{shopId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "user follows a shop ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "shop id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),@ApiResponse(responseCode = "400 Bad Request",
            description = "if a user follows shop already",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public void followShop(@CurrentUser User user,
                           @PathVariable Long shopId){
        shopService.followShop(user,shopId);
    }

    @GetMapping("/search/{searchName}")
    @Operation(summary = "search for shops",description = "used in search bar,returns 10 shops")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public List<ShopSearchResponse> getFirstTenShopSearch(@PathVariable String searchName){
        return shopService.getFirstTenShopSearch(searchName);
    }

    @GetMapping("/{page}/{size}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "get all shops",description = "get all shops. This endpoint is only for admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public PagedResponse<ShopResponse> getAllShops(@PathVariable int size,@PathVariable int page){
        return shopService.getAllShops(size,page);
    }

    @GetMapping("disabledShops/{page}/{size}")
    @PreAuthorize("hasRole('ADMIN')")
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
    public PagedResponse<ShopResponse> getAllDisabled(@PathVariable int size,@PathVariable int page){
        return shopService.getAllDisabled(size,page);
    }

    @PutMapping("disableShop/{shopId}")
    @PreAuthorize("hasRole('ADMIN')")
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
        shopService.disableShop(shopId);
    }

    @PutMapping("enableShop/{shopId}")
    @PreAuthorize("hasRole('ADMIN')")
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
        shopService.enableShop(shopId);
    }

    @DeleteMapping("deleteShop/{shopId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "delete a shop by id",description = "delete a shop. This endpoint is only for admin")
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
    public void deleteShop(@PathVariable Long shopId){
        shopService.deleteShop(shopId);
    }
}
