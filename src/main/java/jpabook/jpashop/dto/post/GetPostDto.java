package jpabook.jpashop.dto.post;

import jpabook.jpashop.domain.wish.Photo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class GetPostDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private Post post;
    }

    @Getter
    @Setter
    public static class Post{
        private String title;
        private String content;
        private String category;
        private int isPrivate;
        private int isCompleted;
        private int isParticipate;
        private int viewCount;
        private int likeCount;
        private LocalDateTime createdAt;
        private List<Long> photoIdList;
        private List<Comment> commentList;
    }

    @Getter
    @Setter
    public static class Comment {
        private String content;
        private String userName;
        private Boolean secret;
        private LocalDateTime createdAt;
        private List<Comment> commentList;
    }

}
