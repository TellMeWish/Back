package jpabook.jpashop.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class GetPostListDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private List<Post> postList;
    }

    @Getter
    @Setter
    public static class Post{
        private Long id;
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
        //private List<Likes> likesList;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        private Long photoId;                     //썸네일용 대표 이미지 아이디 하나를 받아온다
        private List<GetPostDto.Comment> commentList;
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
        private List<GetPostDto.Comment> commentList;
    }
}
