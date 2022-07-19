package jpabook.jpashop.domain.wish;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    //테이블 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    //로그인하는 아이디
    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String phone_num;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "post_user_id", fetch=FetchType.EAGER)
    private List<Post> posts = new ArrayList<>();

}
