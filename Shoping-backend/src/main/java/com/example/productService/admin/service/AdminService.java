package com.example.productService.admin.service;


import com.example.productService.admin.dto.AppDataResponse;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.shop.dto.ShopResponse;
import com.example.productService.users.dto.ManagerInfo;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    PagedResponse<ManagerInfo> findAllManagersAreDisabled(int size, int page);

    void enableManagerToCreateShop(Long managerId);

    void disableManagerToCreateShop(Long managerId);

    PagedResponse<ShopResponse> getAllDisabledShops(int size, int page);

    void disableShop(Long shopId);

    void enableShop(Long shopId);

    AppDataResponse dataResponse();
}
