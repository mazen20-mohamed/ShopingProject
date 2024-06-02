package com.example.productService.shop.service;

import com.example.productService.model.auth.User;
import com.example.productService.model.shop.Shop;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.shop.dto.ShopRequest;
import com.example.productService.shop.dto.ShopResponse;
import com.example.productService.shop.dto.ShopSearchResponse;
import com.example.productService.users.dto.ManagerInfo;
import com.example.productService.users.dto.UserInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShopService {

    Long addStoreWithBranches(ShopRequest shopRequest,  User user);
    void addPhotoShop(MultipartFile file, Long id) throws IOException;
    ShopResponse getShopById(Long id) throws IOException;
    PagedResponse<ShopResponse> getShopByCity(String city , int page, int size);
    PagedResponse<ShopResponse> getShopByGovernment(String government, int page,int size);
    ManagerInfo getManagerOfShop(Long id);
    void deleteShop(Long shopId);
    PagedResponse<ShopResponse> getAllShops(int size, int page);
    List<ShopSearchResponse> getFirstTenShopSearch(String searchName);
    void followShop(User user, Long shopId);
    ResponseEntity<Resource> getShopPhoto(String fileName, HttpServletRequest request);
    Shop getShopByIdOptional(Long id);
}
