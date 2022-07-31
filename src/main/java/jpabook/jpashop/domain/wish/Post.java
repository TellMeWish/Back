package jpabook.jpashop.domain.wish;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String title;

    @Column(name="content", columnDefinition = "TEXT", nullable = false)
    private String content;

    //공개 비공개
    @Column(name="is_private", columnDefinition = "TINYINT")
    private int isPrivate;

    //모집 완료
    @Column(name="is_completed", columnDefinition = "TINYINT")
    private int isCompleted;

    // length=1, nullable = false

    //참여 미참여
    @Column(name="is_Participate", columnDefinition = "TINYINT")
    private int isParticipate;

    @Column(name="view_count")
    private int viewCount;

    @Column(name="like_count")
    private int likeCount;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"post"})
    private List<Likes> likesList = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User post_user_id;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "parent_id is null")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(orphanRemoval = true)
    private List<File> files = new ArrayList<>();
}