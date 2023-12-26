package com.example.productService.model;

import com.example.productService.model.shop.Shop;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private List<String> imagesAndVideos;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id",referencedColumnName = "id")
    private Shop shop;
}
