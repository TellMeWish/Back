package jpabook.jpashop.domain.wish;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.dto.post.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Likes {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="like_id")
  private Long id;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="post_id")
  @JsonIgnore
  private Post post;

  @ManyToOne
  @JoinColumn(name="user_id")
  @JsonIgnore
  private User user;

  @Builder
  public Likes(Post post, User user){
    this.post = post;
    this.user = user;
  }
}
