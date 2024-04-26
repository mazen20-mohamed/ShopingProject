package com.example.productService.model.post;

import com.example.productService.model.DateEntity;
import com.example.productService.model.shop.Shop;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ElementCollection
    @CollectionTable(name = "images_Videos_Url_post_table",
            joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "imagesAndVideosUrl")
    private List<String> imagesAndVideosUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false)
    private Shop shop;

    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL)
    private List<Like> likedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
