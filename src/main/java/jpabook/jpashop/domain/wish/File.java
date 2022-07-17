package jpabook.jpashop.domain.wish;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class File {
    @Id
    @GeneratedValue
    @Column(name="file_id")
    private Long id;

    @ManyToOne(fetch=LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @Column(name="file_url")
    private String fileUrl;

    @Column(name="file_ori_name")
    private String fileOriName;

    @Column(name="file_name")
    private String fileName;
}
