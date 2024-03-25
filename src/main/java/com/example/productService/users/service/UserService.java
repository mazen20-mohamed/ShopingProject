package com.example.productService.users.service;

import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.auth.User;
import com.example.productService.repository.UserRepository;
import com.example.productService.shop.dto.ShopSearchResponse;
import com.example.productService.users.dto.UserInfoResponse;
import com.example.productService.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserInfoResponse getUserData(User user){
        return ModelMapper.convertUserDTO(user);
    }

    public User getUserById(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new NotFoundResponseException("There is no user with id"+userId);

        return user.get();
    }

    public List<UserInfoResponse> getFirstTenUserSearch(String searchName){
        Pageable pageable = PageRequest.of(0, 10);
        return userRepository.findFirstTenByNameContaining(searchName,pageable)
                .stream()
                .map(ModelMapper::convertUserDTO)
                .collect(Collectors.toList());
    }

    public List<UserInfoResponse> findAllManagersAreDisabled(){
        Optional<List<User>> users = userRepository.findAllManagersAndDisabled();
        if(users.isEmpty())
            throw new NotFoundResponseException("There are no managers that are disabled");
        return users.get().stream().map(ModelMapper::convertUserDTO).toList();
    }

    public void enableManagerToCreateShop(Long managerId){
        User user = getUserById(managerId);
        user.setEnabledToCreateShop(true);
        userRepository.save(user);
    }

    public boolean isEnabledManagerToCreateShop(User user){
        return user.isEnabledToCreateShop();
    }
}
