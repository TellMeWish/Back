package jpabook.jpashop.dto.post;


import jpabook.jpashop.domain.wish.Location;
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
        private String content; //댓글내용
        private String title;
        private String category;
        private Integer isParticipate;
        private Integer isPrivate;
        private List<MultipartFile> files;
        private Location location;
        private int isProgress;
    }

    @Getter
    @Setter
    public static class Location{
        private float latitude;
        private float longitude;
    }

    public static class Response{

    }


}
