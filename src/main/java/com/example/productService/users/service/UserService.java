package com.example.productService.users.service;

import com.example.productService.config.JwtService;
import com.example.productService.exception.BadRequestResponseException;
import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.auth.User;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.repository.UserRepository;
import com.example.productService.shop.dto.ShopSearchResponse;
import com.example.productService.users.dto.ChangePasswordRequest;
import com.example.productService.users.dto.ManagerInfo;
import com.example.productService.users.dto.UserInfoResponse;
import com.example.productService.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
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

    // need to check
    public PagedResponse<ManagerInfo> findAllManagersAreDisabled(int size,int page){
        Pageable pageable = PageRequest.of(page,size);
        Page<User> users = userRepository.findAllManagersAndDisabled(pageable);
        List<ManagerInfo> managerInfo = users.getContent()
                .stream().map(ModelMapper::convertManagerDto).toList();
        return new PagedResponse<>(managerInfo, users.getNumber(),
                users.getSize(), managerInfo.size(),
                users.getTotalPages(), users.isLast());
    }

    public void enableManagerToCreateShop(Long managerId){
        User user = getUserById(managerId);
        user.setEnabledToCreateShop(true);
        userRepository.save(user);
    }

    public boolean isEnabledManagerToCreateShop(User user){
        return user.isEnabledToCreateShop();
    }

    public boolean isTokenExpired(String token){
        String jwt = token.substring(7);
        return jwtService.isTokenExpired(jwt);
    }

    public void changePassword(User user, ChangePasswordRequest changePasswordRequest){
        if(! user.getPassword().equals(passwordEncoder.encode(changePasswordRequest.getLastPassword()))){
           throw new BadRequestResponseException("past password is not equal to current password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getChangedPassword()));
        userRepository.save(user);
    }

}
