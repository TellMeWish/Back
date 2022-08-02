package jpabook.jpashop.dto.post;


import jpabook.jpashop.domain.wish.Photo;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class CreatePostDto {

    @Getter
    @Setter
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        private Long userId;//로그인 사용자 PK
        private String content; //댓글내용
        private String title;
        private String category;
        private Integer isParticipate;
        private Integer isPrivate;
        private List<MultipartFile> files;
    }

    public static class Response{

    }


}
