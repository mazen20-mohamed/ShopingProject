package com.example.productService.users.service;

import com.example.productService.config.JwtService;
import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.auth.User;
import com.example.productService.model.shop.Shop;
import com.example.productService.repository.ShopRepository;
import com.example.productService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ShopRepository shopRepository;

    /*
    * this method get token from the request and extract the email of the user
    * then checks if he in the system ?
    * then return the object of the user
     */
    public User getUserFromToken(String token){
        String jwtToken = token.substring(7);
        String email = jwtService.extractUsername(jwtToken);
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw  new NotFoundResponseException("The user is unknown try to login again");
        }
        return user.get();
    }


    public void followShop(Long id, String token){
        User user  = getUserFromToken(token);
        Optional<Shop> shop = shopRepository.findById(id);
        if(shop.isEmpty()) {
            throw new NotFoundResponseException("Shop with id "+ id +"not found");
        }
        shop.get().getFollowers().add(user);
        shopRepository.save(shop.get());
    }

}
