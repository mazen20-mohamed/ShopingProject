package com.example.productService.post.service;
import com.example.productService.exception.BadRequestResponseException;
import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.file.FileStorageService;
import com.example.productService.model.post.Like;
import com.example.productService.model.post.Post;
import com.example.productService.model.auth.User;
import com.example.productService.model.shop.Shop;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.post.dto.PostRequest;
import com.example.productService.post.dto.PostResponse;
import com.example.productService.repository.post.LikeRepository;
import com.example.productService.repository.post.PostRepository;
import com.example.productService.shop.service.ShopServiceImpl;
import com.example.productService.util.ModelMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.hibernate.validator.constraints.time.DurationMax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.io.File;
import java.io.IOException;
import java.io.NotActiveException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ShopServiceImpl shopService;
    private final FileStorageService fileStorageService;
    private final LikeRepository likeRepository;
    public static String IMAGES_POST = System.getProperty("user.dir") + "/photos/post";

    private void createDirectories(Shop shop,Post post) throws IOException {
        Path p = Paths.get(IMAGES_POST+"/"+shop.getName());
        if(!Files.exists(p)){
            Files.createDirectories(p);
        }

        Path path = Paths.get(IMAGES_POST+"/"+shop.getName()+"/post"+post.getId());
        if(!Files.exists(path)){
            Files.createDirectory(path);
        }
    }
    private void deleteDirectoriesOfPost(Post post) {
        Path path = Paths.get(IMAGES_POST+"/"+post.getShop().getName()+"/post"+post.getId());
        if(Files.exists(path)){
            File file = new File(String.valueOf(path));
            deleteFilesInDirectory(file);
            boolean deleted = file.delete();
            log.info(path+ " deleted "+deleted);
        }
    }
    private void deleteFilesInDirectory(File file){
        for (File subFile : Objects.requireNonNull(file.listFiles())){
            subFile.delete();
        }
    }
    private List<String> saveImagesAndVideos(PostRequest postRequest
            , Post post) throws IOException {
        List<String> imageNames = new ArrayList<>();
        for(MultipartFile file:postRequest.getImgsAndVideosOfPost()){
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("api/v1/post/"+post.getShop().getName()+"/post"+post.getId()+"/")
                    .path(Objects.requireNonNull(file.getOriginalFilename()))
                    .toUriString();
            Path path = Paths.get(IMAGES_POST+"/"+post.getShop().getName()+"/post"+post.getId()+"/"+file.getOriginalFilename());
            imageNames.add(fileDownloadUri);
            Files.write(path, file.getBytes());
        }
        return imageNames;
    }

    public Post getPostByIdCheck(Long id){
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            throw new NotFoundResponseException("There is no post with "+id);
        }
        return post.get();
    }

    public void createPost(PostRequest postRequest,
                           Long shopId)  throws IOException {

        Shop shop = shopService.getShopByIdOptional(shopId);

        if(postRequest.getImgsAndVideosOfPost().get(0).isEmpty()) {
            log.info("There is no images or videos uploaded");
            throw new BadRequestResponseException("There is no images or videos uploaded");
        }

        Post post = Post.builder()
                .description(postRequest.getDescription())
                .shop(shop)
                .build();
        post = postRepository.save(post);

        createDirectories(shop,post);

        post.setImagesAndVideosUrl(saveImagesAndVideos(postRequest,post));
        postRepository.save(post);
        log.info("Post id "+post.getId()+" is created in shop Id "+shopId);
    }

    public PostResponse getPostById(Long id,User user){
        Post post = getPostByIdCheck(id);
        return ModelMapper.convertPostDTO(post,user);
    }

    public void updatePost(PostRequest postRequest , Long postId) throws IOException {
        Post post = getPostByIdCheck(postId);
        deleteDirectoriesOfPost(post);
        createDirectories(post.getShop(),post);
        post.setDescription(postRequest.getDescription());
        post.setImagesAndVideosUrl(saveImagesAndVideos(postRequest,post));
        postRepository.save(post);
    }

    public void deletePost(Long postId){
        Post post = getPostByIdCheck(postId);
        deleteDirectoriesOfPost(post);
        postRepository.delete(post);
        log.info("Post is deleted with id "+postId);
    }

    public void increaseLike(Long postId, User user) {
        Post post = getPostByIdCheck(postId);
        boolean isLiked = post.getLikedUsers().stream()
                .anyMatch(like -> Objects.equals(like.getUser().getId(), user.getId()));
        if(isLiked){
            throw new BadRequestResponseException("User has liked the post "+postId+" before");
        }
        Like like = Like.builder()
                .post(post)
                .user(user)
                .build();
        likeRepository.save(like);
    }

    public void decreaseLike(Long postId, User user) {
        getPostByIdCheck(postId);
        Optional<Like> like =  likeRepository.findByUserAndPost(user.getId(),postId);
        if(like.isEmpty()){
            throw new BadRequestResponseException("There is no like with user id "+user.getId() + " with post id "+postId) ;
        }
        likeRepository.delete(like.get());
    }

    public ResponseEntity<Resource> getPostImgOrVideo(
                        String shopName
                        ,Long postId
                        ,String fileName){
        String path = IMAGES_POST + "/" + shopName + "/post"+postId+"/";
        // Load file as Resource
        Resource resource = fileStorageService.loadStoredImgAsResource(path,fileName);

        return ResponseEntity.ok()
                .body(resource);
    }

    // timeline posts for each user
    public PagedResponse<PostResponse> getAllPosts(int page , int size , User user){

        // Retrieve posts
        Pageable pageable = PageRequest.of(page, size,Sort.by("createdAt").descending());

        List<Long>followingShops = new ArrayList<>();
        for(Shop shop : user.getShopsFollowing()){
            followingShops.add(shop.getId());
        }

        Page<Post> posts = postRepository.findAllPostsByFollowedUser(followingShops,pageable);


        List<PostResponse> postResponses = posts.getContent().stream().map(post -> {
            return ModelMapper.convertPostDTO(post, user);
        }).toList();

        return new PagedResponse<>(postResponses, posts.getNumber(),
                posts.getSize(), posts.getTotalElements(),
                posts.getTotalPages(), posts.isLast());
    }

    public PagedResponse<PostResponse> getAllPostsOfShop(User user,Long shopId , int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        shopService.getShopByIdOptional(shopId);
        Page<Post> posts = postRepository.getPostsOfShop(shopId,pageable);

        List<PostResponse> postResponses = posts.map(
                post -> {
                    return ModelMapper.convertPostDTO(post,user);
                }
        ).stream().toList();

        return new PagedResponse<>(postResponses,posts.getNumber(),
                posts.getSize(), posts.getTotalElements(),
                posts.getTotalPages(), posts.isLast());
    }
}
