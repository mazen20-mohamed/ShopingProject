package com.example.productService.post;

import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.Post;
import com.example.productService.model.shop.Shop;
import com.example.productService.post.dto.PostRequest;
import com.example.productService.post.dto.PostResponse;
import com.example.productService.repository.PostRepository;
import com.example.productService.shop.service.ShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ShopServiceImpl shopService;
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
    private void deleteDirectoriesOfPost(Post post) throws IOException {
        Path path = Paths.get(IMAGES_POST+"/"+post.getShop().getName()+"/post"+post.getId());
        if(Files.exists(path)){
            Files.delete(path);
        }
    }
    private List<String> saveImagesAndVideos(PostRequest postRequest , Post post) throws IOException {
        List<String> imageNames = new ArrayList<>();
        for(MultipartFile file:postRequest.getMultipartFiles()){
            Path path = Paths.get(IMAGES_POST+"/"+post.getShop().getName()+"/post"+post.getId()+"/"+file.getOriginalFilename());
            imageNames.add(path.toString());
            Files.write(path, file.getBytes());
        }
        return imageNames;
    }

    public void createPost(PostRequest postRequest, Long shopId)  throws IOException {
        Shop shop = shopService.getShopByIdOptional(shopId);


        Post post = Post.builder()
                .description(postRequest.getDescription())
                .shop(shop)
                .build();
        post = postRepository.save(post);

        createDirectories(shop,post);

        post.setImagesAndVideos(saveImagesAndVideos(postRequest,post));
    }

    public PostResponse getPostById(Long id) throws IOException {
        Post post = getPostByIdCheck(id);
        return convertPostDTO(post);
    }
    private PostResponse convertPostDTO(Post post) throws IOException {
        List<byte[]> files = new ArrayList<>();
        for(String file : post.getImagesAndVideos()){
            Path fileNameAndPath = Paths.get(file);
            byte[] imageBytes = null ;
            if(Files.exists(fileNameAndPath)){
                imageBytes = Files.readAllBytes(fileNameAndPath);
            }
            files.add(imageBytes);
        }
        return PostResponse.builder()
                .description(post.getDescription())
                .multipartFiles(files)
                .build();
    }

    public Post getPostByIdCheck(Long id){
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            throw new NotFoundResponseException("There is no post with "+id);
        }
        return post.get();
    }

    public void updatePost(PostRequest postRequest , Long postId) throws IOException {
        Post post = getPostByIdCheck(postId);
        deleteDirectoriesOfPost(post);
        createDirectories(post.getShop(),post);
        post.setDescription(postRequest.getDescription());
        post.setImagesAndVideos(saveImagesAndVideos(postRequest,post));
    }

    public void deletePost(Long postId) throws IOException{
        Post post = getPostByIdCheck(postId);
        deleteDirectoriesOfPost(post);
        postRepository.delete(post);
    }
}
