package jpabook.jpashop.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;

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
        private User post_user_id;
        private String title;
        private String content;
        private String category;
        private int isPrivate;
        private int isCompleted;
        private int isParticipate;
        private int isProgress;
        private int viewCount;
        private int likeCount;
        private Location location;
       //private List<Likes> likesList;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        private List<Long> photoIdList;
        private List<Comment> commentList;
    }

    @Getter
    @Setter
    public static class User{
        private String username;
        private String nickname;
    }

    @Getter
    @Setter
    public static class Location{
        private float latitude;
        private float longitude;
    }

    @Getter
    @Setter
    public static class Comment {
        private Long id;
        private String content;
        private User user;
        private Boolean secret;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        private List<Comment> commentList;
    }

}
