package com.example.productService.shop.service;

import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.auth.User;
import com.example.productService.model.shop.Branch;
import com.example.productService.model.shop.Phone;
import com.example.productService.model.shop.Shop;
import com.example.productService.repository.BranchRepository;
import com.example.productService.repository.PhoneRepository;
import com.example.productService.repository.ShopRepository;
import com.example.productService.shop.dto.BranchRequest;
import com.example.productService.shop.dto.BranchResponse;
import com.example.productService.shop.dto.ShopRequest;
import com.example.productService.shop.dto.ShopResponse;
import com.example.productService.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final BranchRepository branchRepository;
    private final PhoneRepository phoneRepository;
    private final UserService userService;
    public static String IMAGES_SHOP = System.getProperty("user.dir") + "/photos/shop";

    public void addStoreWithBranches(ShopRequest shopRequest, String token) {
        User user = userService.getUserFromToken(token);

        Shop shop = Shop.builder()
                        .name(shopRequest.getName())
                        .numberOfRates(0L)
                        .rate(5.0)
                        .category(shopRequest.getCategory())
                        .description(shopRequest.getDescription())
                .manager(user)
                .build();

        shop = shopRepository.save(shop);

        for (BranchRequest i : shopRequest.getBranchRequests()){
            Branch branch = Branch.builder()
                    .building_number(i.getBuilding_number())
                    .city(i.getCity())
                    .shop(shop)
                    .street(i.getStreet())
                    .country(i.getCountry())
                    .government(i.getGovernment())
                    .location(i.getLocation())
                    .build();
            branch = branchRepository.save(branch);
            for (String p : i.getPhones()){
                Phone phone = Phone.builder()
                        .phone(p)
                        .branchPhone(branch)
                        .build();
                phoneRepository.save(phone);
            }
        }
    }

    public void addPhotoShop(MultipartFile file,Long id) throws IOException {
        Shop shop = getShopByIdOptional(id);
        Path fileNameAndPath = Paths.get(IMAGES_SHOP, (shop.getName() + shop.getManager().getEmail()+".png"));
        shop.setImageName( IMAGES_SHOP + (shop.getName() + shop.getManager().getEmail()+".png"));
        Files.write(fileNameAndPath, file.getBytes());
    }

    private ShopResponse ConvertShopDTO(Shop shop) throws IOException {
//        if(!shop.isApproved()){
//            return null;
//        }

        Path fileNameAndPath = Paths.get(shop.getImageName());
        byte[] imageBytes = null ;
        if(Files.exists(fileNameAndPath)){
            imageBytes = Files.readAllBytes(fileNameAndPath);
        }

        return ShopResponse.builder()
                .id(shop.getId())
                .name(shop.getName())
                .category(shop.getCategory())
                .description(shop.getDescription())
                .branchRequests(shop.getBranches().stream()
                        .map(this::ConvertBranchDTO)
                        .collect(Collectors.toList()))
                .numberOfFollowers(shop.getFollowers().size())
                .rate(shop.getRate())
                .shopImage(imageBytes)
                .build();
    }

    private BranchResponse ConvertBranchDTO(Branch branch){
        return BranchResponse.builder()
                .id(branch.getId())
                .city(branch.getCity())
                .location(branch.getLocation())
                .government(branch.getGovernment())
                .country(branch.getCountry())
                .building_number(branch.getBuilding_number())
                .street(branch.getStreet())
                .phones(branch.getPhoneList().stream()
                        .map(Phone::getPhone)
                        .collect(Collectors.toList()))
                .build();
    }

    public ShopResponse getShopById(Long id) throws IOException {
        Shop shop = getShopByIdOptional(id);
        return ConvertShopDTO(shop);
    }

    public List<ShopResponse> getShopByCity(String city){
        Optional<List<Branch>> branch =  branchRepository.findByCity(city);

        return branch.map(branches -> branches.stream().map(
                        branch1 -> {
                            try {
                                return ConvertShopDTO(branch1.getShop());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).distinct().filter(Objects::nonNull)
                .collect(Collectors.toList())).orElse(new ArrayList<>());
    }
    public List<ShopResponse> getShopByGovernment(String government){
        Optional<List<Branch>> branch =  branchRepository.findByGovernment(government);
        return branch.map(branches -> branches.stream().map(
                        branch1 -> {
                            try {
                                return ConvertShopDTO(branch1.getShop());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).distinct().filter(Objects::nonNull)
                .collect(Collectors.toList())).orElse(new ArrayList<>());
    }

    public Long getManagerOfShop(Long id){
        Shop shop = getShopByIdOptional(id);
        return shop.getManager().getId();
    }

    public Shop getShopByIdOptional(Long id){
        Optional<Shop> shop =  shopRepository.findById(id);
        if(shop.isEmpty()){
            throw  new NotFoundResponseException("Shop is not found with "+ id);
        }

        return shop.get();
    }
}
