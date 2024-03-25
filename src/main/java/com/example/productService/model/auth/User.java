package com.example.productService.model.auth;

import com.example.productService.model.DateEntity;
import com.example.productService.model.notification.ManagerNotification;
import com.example.productService.model.notification.UserNotification;
import com.example.productService.model.post.Comment;
import com.example.productService.model.post.Like;
import com.example.productService.model.shop.Shop;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends DateEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    @Email
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean enabledToCreateShop = false;

    // this relation is with manager and shop only
    @OneToOne(mappedBy = "manager")
    private Shop ownShop;

    @ManyToMany(mappedBy = "followers",fetch = FetchType.EAGER)
    private List<Shop> shopsFollowing;

    @OneToMany(mappedBy = "user" ,  cascade = CascadeType.ALL)
    private List<Like> likedPosts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserNotification> userNotifications;

    @OneToMany(mappedBy = "manager")
    private List<ManagerNotification> managerNotifications;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
