package com.example.productService.admin.service;

import com.example.productService.admin.dto.AppDataResponse;
import com.example.productService.model.auth.User;
import com.example.productService.model.shop.Shop;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.repository.UserRepository;
import com.example.productService.repository.post.CommentRepository;
import com.example.productService.repository.post.LikeRepository;
import com.example.productService.repository.post.PostRepository;
import com.example.productService.repository.shop.ShopRepository;
import com.example.productService.shop.dto.ShopResponse;
import com.example.productService.shop.service.ShopService;
import com.example.productService.users.dto.ManagerInfo;
import com.example.productService.users.service.UserService;
import com.example.productService.util.ModelMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final UserRepository userRepository;
    private final UserService userService;
    private final ShopRepository shopRepository;
    private final ShopService shopService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public PagedResponse<ManagerInfo> findAllManagersAreDisabled(int size, int page){
        Pageable pageable = PageRequest.of(page,size);
        Page<User> users = userRepository.findAllManagersAndDisabled(pageable);
        List<ManagerInfo> managerInfo = users.getContent()
                .stream().map(ModelMapper::convertManagerDto).toList();
        return new PagedResponse<>(managerInfo, users.getNumber(),
                users.getSize(), managerInfo.size(),
                users.getTotalPages(), users.isLast());
    }
    public void enableManagerToCreateShop(Long managerId){
        User user = userService.getUserById(managerId);
        user.setEnabledToCreateShop(true);
        userRepository.save(user);
    }

    public void disableManagerToCreateShop(Long managerId){
        User user = userService.getUserById(managerId);
        user.setEnabledToCreateShop(false);
        userRepository.save(user);
    }

    public PagedResponse<ShopResponse> getAllDisabledShops(int size, int page){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Shop> shopResponses = shopRepository.findAllDisabledShops(pageable);
        List<ShopResponse> shopResponseList = shopResponses.getContent().
                stream().map(ModelMapper::ConvertShopDTO).toList();
        return new PagedResponse<>(shopResponseList, shopResponses.getNumber(),
                shopResponses.getSize(), shopResponseList.size(),
                shopResponses.getTotalPages(), shopResponses.isLast());
    }

    public void disableShop(Long shopId){
        Shop shop = shopService.getShopByIdOptional(shopId);
        shop.setEnabled(false);
        shopRepository.save(shop);
    }

    public void enableShop(Long shopId){
        Shop shop = shopService.getShopByIdOptional(shopId);
        shop.setEnabled(true);
        shopRepository.save(shop);
    }
    public AppDataResponse dataResponse(){
        return ModelMapper.convertData(userRepository.count(),shopRepository.count(),
                commentRepository.count(),likeRepository.count(),postRepository.count());
    }
}
