package jpabook.jpashop.domain.wish;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "file")
public class Photo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    //저장경로
    @Column(name="file_url")
    private String fileUrl;

    //원본파일명
    @Column(name="file_ori_name")
    private String fileOriName;

    @Column(name="file_name")
    private String fileName;

    private Long fileSize;

    @Builder
    public Photo(Post post, String fileOriName, String fileName, String fileUrl, Long fileSize) {
        this.post =post;
        this.fileOriName = fileOriName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
    }

    @Builder
    public Photo(String fileOriName, String fileUrl, Long fileSize) {
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
    }

    public void setPost(Post post) {
        this.post = post;

        if(!post.getPhotos().contains(this))
            post.getPhotos().add(this);
    }
}
