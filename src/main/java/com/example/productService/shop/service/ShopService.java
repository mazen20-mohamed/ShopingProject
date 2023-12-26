package com.example.productService.shop.service;

import com.example.productService.shop.dto.ShopRequest;
import com.example.productService.shop.dto.ShopResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ShopService {

    void addStoreWithBranches(ShopRequest shopRequest, String token);
    void addPhotoShop(MultipartFile file, Long id) throws IOException;
    ShopResponse getShopById(Long id) throws IOException;
    List<ShopResponse> getShopByCity(String city);

    List<ShopResponse> getShopByGovernment(String government);
    Long getManagerOfShop(Long id);

}
