package jpabook.jpashop.dto;


import lombok.Getter;
import lombok.Setter;


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
    }

    public static class Response{

    }


}
