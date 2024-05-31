package com.example.productService.shop.service;
import com.example.productService.exception.BadRequestResponseException;
import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.file.FileStorageService;
import com.example.productService.model.auth.Role;
import com.example.productService.model.auth.User;
import com.example.productService.model.post.Post;
import com.example.productService.model.shop.*;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.repository.shop.*;
import com.example.productService.shop.dto.BranchRequest;
import com.example.productService.shop.dto.ShopRequest;
import com.example.productService.shop.dto.ShopResponse;
import com.example.productService.shop.dto.ShopSearchResponse;
import com.example.productService.users.dto.UserInfoResponse;
import com.example.productService.util.ModelMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

/************************************* auto wired variables ****************************************************/
    private final ShopRepository shopRepository;
    private final BranchRepository branchRepository;
    private final PhoneRepository phoneRepository;
    private final FileStorageService fileStorageService;
    public static String IMAGES_SHOP = System.getProperty("user.dir") + "/photos/shop/";

/******************************* functions of shop Service ********************************************/
// This creates a store with branches ...
    // done and tested

public Long addStoreWithBranches(ShopRequest shopRequest,User user) {

    if(user.getOwnShop() != null ){
        throw new BadRequestResponseException("You have shop already with name "+user.getOwnShop().getName());
    }

    if(!user.isEnabledToCreateShop()) {
        throw new BadRequestResponseException("You can not create shop because you are not enabled from the admin");
    }

    Shop shop = Shop.builder()
            .name(shopRequest.getName())
            .numberOfRates(0L)
            .rate(5.0)
//            .category(shopRequest.getCategory())
            .description(shopRequest.getDescription())
            .manager(user)
            .enabled(true)
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
    return shop.getId();
}
    // add photo to current store has been created ...
    // done and tested

    public void addPhotoShop(MultipartFile file,Long id) throws IOException {
        Shop shop = getShopByIdOptional(id);
        Path fileNameAndPath = Paths.get(IMAGES_SHOP, shop.getName()+shop.getId()+".png");
        Path p = Paths.get(IMAGES_SHOP);
        if(!Files.exists(p)){
            Files.createDirectories(p);
        }
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/v1/shop/photo/")
                .path(shop.getName() + shop.getId() + ".png")
                .toUriString();

        shop.setImagePathUrl(fileDownloadUri);
        shopRepository.save(shop);
        Files.write(fileNameAndPath, file.getBytes());
    }

    // get photo by link from my server
    // done and tested

    public ResponseEntity<Resource> getShopPhoto(String fileName, HttpServletRequest request){
        // Load file as Resource
        Resource resource = fileStorageService.loadStoredImgAsResource(IMAGES_SHOP,fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new BadRequestResponseException("");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // get shop by its id
    // done and tested
    public ShopResponse getShopById(Long id) {
        Shop shop = getShopByIdOptional(id);
        return ModelMapper.ConvertShopDTO(shop);
    }

    // done and need to be tested
    public PagedResponse<ShopResponse> getShopByCity(String city, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Branch> branches =  branchRepository.findByCity(city,pageable);

        List<ShopResponse> shopResponse = branches.getContent().stream().map(branch -> {
            return ModelMapper.ConvertShopDTO(branch.getShop());
        }).distinct().toList();

        return new PagedResponse<>(shopResponse, branches.getNumber(),
                branches.getSize(), shopResponse.size(),
                branches.getTotalPages(), branches.isLast());
    }
    // done and need to be tested
    public PagedResponse<ShopResponse> getShopByGovernment(String government,int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Branch> branches =  branchRepository.findByGovernment(government,pageable);

        List<ShopResponse> shopResponse = branches.getContent().stream().map(branch -> {
            return ModelMapper.ConvertShopDTO(branch.getShop());
        }).distinct().toList();
        return new PagedResponse<>(shopResponse, branches.getNumber(),
                branches.getSize(), shopResponse.size(),
                branches.getTotalPages(), branches.isLast());
    }
    // done and need to be tested
    public UserInfoResponse getManagerOfShop(Long id){
        Shop shop = getShopByIdOptional(id);
        return ModelMapper.convertUserDTO(shop.getManager());
    }

    // done and tested
    public void followShop(User user, Long shopId){
        Shop shop = getShopByIdOptional(shopId);
        boolean check = shopRepository.checkIfUserFollowShop(user.getId(),shopId);
        if(check){
            throw new BadRequestResponseException("User "+user.getFirstname()+" has been following the shop");
        }

        shop.getFollowers().add(user);
        shopRepository.save(shop);
    }

    public List<ShopSearchResponse> getFirstTenShopSearch(String searchName){
        Pageable pageable = PageRequest.of(0, 10);
        return shopRepository.findFirstTenByNameContaining(searchName,pageable)
                .stream()
                .map(ModelMapper::convertShopSearchDTO)
                .collect(Collectors.toList());
    }

    public PagedResponse<ShopResponse> getAllShops(int size, int page){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Shop> shopResponses = shopRepository.findAllShops(pageable);
        List<ShopResponse> shopResponseList = shopResponses.getContent()
                .stream().map(ModelMapper::ConvertShopDTO).toList();
        return new PagedResponse<>(shopResponseList, shopResponses.getNumber(),
                shopResponses.getSize(), shopResponseList.size(),
                shopResponses.getTotalPages(), shopResponses.isLast());
    }

    public PagedResponse<ShopResponse> getAllDisabled(int size,int page){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Shop> shopResponses = shopRepository.findAllDisabledShops(pageable);
        List<ShopResponse> shopResponseList = shopResponses.getContent().
                stream().map(ModelMapper::ConvertShopDTO).toList();
        return new PagedResponse<>(shopResponseList, shopResponses.getNumber(),
                shopResponses.getSize(), shopResponseList.size(),
                shopResponses.getTotalPages(), shopResponses.isLast());
    }

    public void disableShop(Long shopId){
        Shop shop = getShopByIdOptional(shopId);
        shop.setEnabled(false);
        shopRepository.save(shop);
    }

    public void enableShop(Long shopId){
        Shop shop = getShopByIdOptional(shopId);
        shop.setEnabled(true);
        shopRepository.save(shop);
    }

    public void deleteShop(Long shopId){
        Shop shop = getShopByIdOptional(shopId);
        shopRepository.delete(shop);
    }
/****************************************** helping functions *****************************************************/
    // this function is just help me to get shop and checks if id sends is correct
    public Shop getShopByIdOptional(Long id){
        Optional<Shop> shop =  shopRepository.findById(id);
        if(shop.isPresent()){
            return shop.get();
        }
        throw  new NotFoundResponseException("Shop is not found with "+ id);
    }

}
