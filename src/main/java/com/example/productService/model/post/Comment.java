package com.example.productService.model.post;
import com.example.productService.model.DateEntity;
import com.example.productService.model.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comment_post_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id" , nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_parent_id")
    private Comment commentParent;

    @OneToMany(mappedBy = "commentParent",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
