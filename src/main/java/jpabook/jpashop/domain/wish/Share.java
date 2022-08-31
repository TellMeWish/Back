package jpabook.jpashop.domain.wish;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.dto.post.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_id", nullable = false)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_id")
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    //진행상태 ( 예정 = 0, 진행중 = 1, 완료 = 2 )
    @ColumnDefault("0")
    private int progress;


    @Builder
    public Share(Post post, User user){
        this.post = post;
        this.user = user;

    }


}
