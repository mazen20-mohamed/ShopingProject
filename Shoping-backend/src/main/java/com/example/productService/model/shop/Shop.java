package com.example.productService.model.shop;
import com.example.productService.model.DateEntity;
import com.example.productService.model.post.Post;
import com.example.productService.model.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "shops")
public class Shop extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private long numberOfRates;
    private double rate;
    private String imagePathUrl;
    private boolean enabled = true;

    @OneToMany(mappedBy = "shop" , cascade = CascadeType.ALL)
    private List<Branch> branches = new ArrayList<>();

    @OneToOne(optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @OneToMany(mappedBy = "shop" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Post> posts;

    @ManyToMany
    @JoinTable(
            name = "user_shop",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> followers;
}
