package jpabook.jpashop.domain.wish;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
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
    @Column(name="is_private", columnDefinition = "TINYINT", length=1, nullable = false)
    private int isPrivate;

    //모집 완료
    @Column(name="is_completed", columnDefinition = "TINYINT", length=1, nullable = false)
    private int isCompleted;

    //참여 미참여
    @Column(name="is_Participate", columnDefinition = "TINYINT", length=1, nullable = false)
    private int isParticipate;

    @Column(name="view_count")
    private int viewCount;

    @Column(name="like_count")
    private int likeCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt; // 자동화 추가하기

    @ManyToOne
    @JoinColumn(name = "id")
    private User post_user_id;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

//    @OneToMany(mappedBy="post", cascade=CascadeType.ALL)
//    private List<Category.Comment> comments = new ArrayList<>();

    @OneToMany
    private List<File> files = new ArrayList<>();
}