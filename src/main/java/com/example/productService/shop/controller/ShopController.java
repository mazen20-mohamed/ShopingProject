package com.example.productService.shop.controller;

import com.example.productService.config.CurrentUser;
import com.example.productService.model.auth.User;
import com.example.productService.model.shop.Shop;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.shop.dto.ShopRequest;
import com.example.productService.shop.dto.ShopResponse;
import com.example.productService.shop.dto.ShopSearchResponse;
import com.example.productService.shop.service.ShopServiceImpl;
import com.example.productService.users.dto.UserInfoResponse;
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
public class ShopController {
    private final ShopServiceImpl shopService;
    @PostMapping("/add")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addShopWithBranches(@Valid @RequestBody ShopRequest shopRequest,
                                    @CurrentUser User user) {
        return shopService.addStoreWithBranches(shopRequest,user);
    }

    @PostMapping("/addPhoto/{shopId}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public void addPhotoForShop(@PathVariable Long shopId,
                                @NotNull @RequestParam("file") MultipartFile file)
            throws IOException {
        shopService.addPhotoShop(file,shopId);
    }

    @GetMapping("/{shopId}")
    public ShopResponse getShopById(@PathVariable Long shopId) {
        return shopService.getShopById(shopId);
    }

    @GetMapping("city/{city}/{page}/{size}")
    public PagedResponse<ShopResponse> getShopByCity(@PathVariable String city,
                                                     @PathVariable int page,
                                                     @PathVariable int size ) {
        return shopService.getShopByCity(city,page,size);
    }

    @GetMapping("government/{government}/{page}/{size}")
    public PagedResponse<ShopResponse> getShopByGovernment(@PathVariable String government,
                                                           @PathVariable int page,
                                                           @PathVariable int size) {
        return shopService.getShopByGovernment(government,page,size);
    }

    @GetMapping("/manager/{shopId}")
    public UserInfoResponse getManagerOfShop(@PathVariable Long shopId){
        return shopService.getManagerOfShop(shopId);
    }


    @GetMapping("/photo/{fileName:.+}")
    public ResponseEntity<Resource> getShopPhoto(@PathVariable String fileName,
                                                 HttpServletRequest request){
        return shopService.getShopPhoto(fileName,request);
    }

    @GetMapping("/follow/{shopId}")
    @PreAuthorize("hasRole('USER')")
    public void followShop(@CurrentUser User user,
                           @PathVariable Long shopId){
        shopService.followShop(user,shopId);
    }


    @GetMapping("/search/{searchName}")
    public List<ShopSearchResponse> getFirstTenShopSearch(@PathVariable String searchName){
        return shopService.getFirstTenShopSearch(searchName);
    }

    @GetMapping("/{page}/{size}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ShopResponse> getAllShops(@PathVariable int size,@PathVariable int page){
        return shopService.getAllShops(size,page);
    }

    @GetMapping("disabledShops/{page}/{size}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ShopResponse> getAllDisabled(@PathVariable int size,@PathVariable int page){
        return shopService.getAllDisabled(size,page);
    }

    @PutMapping("disableShop/{shopId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void disableShop(@PathVariable Long shopId){
        shopService.disableShop(shopId);
    }
    @PutMapping("enableShop/{shopId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void enableShop(Long shopId){
        shopService.enableShop(shopId);
    }
    @DeleteMapping("deleteShop/{shopId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteShop(@PathVariable Long shopId){
        shopService.deleteShop(shopId);
    }
}
