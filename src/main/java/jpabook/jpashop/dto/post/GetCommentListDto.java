package jpabook.jpashop.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import jpabook.jpashop.domain.wish.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class GetCommentListDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private List<Comment> commentList;
    }

    @Getter
    @Setter
    public static class Comment{
        private Long id;
        private String content;
        private Builder secret;
        private User user;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        private List<GetCommentListDto.Comment> commentList;
    }
}
