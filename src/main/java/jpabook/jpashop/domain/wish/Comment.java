package jpabook.jpashop.domain.wish;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.dto.post.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Comment extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Comment parent;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_id")
    @JsonIgnore
    private Post post;

    private Boolean secret;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();
}
