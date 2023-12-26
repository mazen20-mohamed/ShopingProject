package com.example.productService.model.shop;

import com.example.productService.model.Post;
import com.example.productService.model.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String category;
    private String description;
    private long numberOfRates;
    private double rate;
    private String imageName;


    @OneToMany(mappedBy = "shop" , cascade = CascadeType.ALL)
    private List<Branch> branches = new ArrayList<>();

    @OneToOne(optional = false)
    @JoinColumn(name = "manager_id",referencedColumnName = "id")
    private User manager;

    @OneToMany(mappedBy = "shop" , cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToMany
    @JoinTable(
            name = "user_shop",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> followers;
}
