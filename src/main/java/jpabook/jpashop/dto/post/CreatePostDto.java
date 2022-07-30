package jpabook.jpashop.dto.post;


import jpabook.jpashop.domain.wish.Photo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class CreatePostDto {

    @Getter
    @Setter
    public static class Request{
        private Long userId;//로그인 사용자 PK
        private String content; //댓글내용
        private String title;
        private String category;
        private int isParticipate;
        private int isPrivate;
        private List<Photo> photos;
    }

    public static class Response{

    }


}
