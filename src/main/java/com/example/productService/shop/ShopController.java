package com.example.productService.shop;

import com.example.productService.shop.dto.ShopRequest;
import com.example.productService.shop.dto.ShopResponse;
import com.example.productService.shop.service.ShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public void addShopWithBranches(@RequestBody ShopRequest shopRequest,
                                    @RequestHeader(name = "Authorization") String token) {
        shopService.addStoreWithBranches(shopRequest,token);
    }

    @PostMapping("/addPhoto/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public void addPhotoForShop(@PathVariable Long id,
                                @RequestParam("file") MultipartFile file) throws IOException {
        shopService.addPhotoShop(file,id);
    }

    @GetMapping("/{id}")
    public ShopResponse getShopById(@PathVariable Long id) throws IOException {
        return shopService.getShopById(id);
    }

    @GetMapping("city/{city}")
    public List<ShopResponse> getShopByCity(@PathVariable String city) {
        return shopService.getShopByCity(city);
    }

    @GetMapping("government/{government}")
    public List<ShopResponse> getShopByGovernment(@PathVariable String government) {
        return shopService.getShopByGovernment(government);
    }

    @GetMapping("/manager/{id}")
    public Long getManagerOfShop(@PathVariable Long id){
        return shopService.getManagerOfShop(id);
    }


}
